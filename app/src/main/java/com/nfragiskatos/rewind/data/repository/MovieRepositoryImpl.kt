package com.nfragiskatos.rewind.data.repository

import android.util.Log
import com.nfragiskatos.rewind.data.local.RewindDatabase
import com.nfragiskatos.rewind.data.mapper.toMovie
import com.nfragiskatos.rewind.data.mapper.toMovieDetails
import com.nfragiskatos.rewind.data.mapper.toMovieEntity
import com.nfragiskatos.rewind.data.remote.TheMovieDbApi
import com.nfragiskatos.rewind.data.remote.dto.media.movie.MovieDetailsDto
import com.nfragiskatos.rewind.data.remote.dto.media.movie.MovieDto
import com.nfragiskatos.rewind.data.remote.dto.media.movie.MoviePagedResultsDto
import com.nfragiskatos.rewind.domain.model.Movie
import com.nfragiskatos.rewind.domain.model.MovieDetails
import com.nfragiskatos.rewind.domain.model.MoviePage
import com.nfragiskatos.rewind.domain.repository.MovieRepository
import com.nfragiskatos.rewind.util.Resource
import com.nfragiskatos.rewind.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: TheMovieDbApi,
    private val db: RewindDatabase
) : MovieRepository {

    private val movieDao = db.movieDao()
    override fun searchMovies(searchTerm: String): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading(true))
        val response: Response<MoviePagedResultsDto>? = try {
            api.searchMoviesByQuery(query = searchTerm)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Error loading data"))
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Error loading data"))
            null
        }
        response?.let {
            if (response.isSuccessful) {
                val body = response.body()
                val movies: List<Movie> = body?.results?.map(MovieDto::toMovie) ?: listOf()
                emit(Resource.Success(movies))
            } else {
                // TODO: Better response code handling
                emit(Resource.Error("Error loading data"))
            }
        }
        emit(Resource.Loading(false))
    }

    override fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>> = flow {
        emit(Resource.Loading(true))

        val response: Response<MovieDetailsDto>? = try {
            api.getMovieDetails(id)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Error loading data"))
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Error loading data"))
            null
        }

        response?.let {
            if (response.isSuccessful) {
                val details: MovieDetails? = response.body()?.toMovieDetails()

                if (details == null) {
                    emit(Resource.Error("Error loading data"))
                } else {
                    emit(Resource.Success(details))
                }
            } else {
                // TODO: Better response code handling
                emit(Resource.Error("Error loading data"))
            }
        }
        emit(Resource.Loading(false))
    }

    override fun addMovieToWatchedHistory(movie: Movie): Flow<Resource<Movie>> = flow {
        emit(Resource.Loading(true))
        try {
            movieDao.insertAll(movie.toMovieEntity())
        } catch (e: Exception) {
            Log.i("REWIND DATABASE", e.printStackTrace().toString())
            emit(Resource.Error("Error saving movie", movie))
        }
        emit(Resource.Loading(false))

    }

    override suspend fun searchMoviesPagingTest(
        searchTerm: String,
        page: Int
    ): Result<MoviePage, Throwable> {
        val response = try {
            api.searchMoviesByQuery(query = searchTerm, page = page)
        } catch (e: IOException) {
            return Result.Failure(e)
        } catch (e: HttpException) {
            return Result.Failure(e)
        }

        return if (response.isSuccessful) {
            val body = response.body()

            val moviePage = if (body != null) {
                val testRet = body.results.map { dto ->
                    val local = movieDao.findById(dto.id)

                    dto.toMovie().also {
                        if (local != null) {
                            it.dateWatched = local.dateWatched
                        }
                    }
                }

                MoviePage(
                    totalPages = body.totalPages,
                    totalResults = body.totalResults,
                    page = body.page,
//                    results = body.results.map(MovieDto::toMovie)
                    results = testRet
                )
            } else {
                return Result.Failure(HttpException(response))
            }

            Result.Success(moviePage)
        } else {
            Result.Failure(HttpException(response))
        }
    }
}