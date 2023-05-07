package com.nfragiskatos.rewind.data.repository

import com.nfragiskatos.rewind.data.mapper.toMovie
import com.nfragiskatos.rewind.data.remote.TheMovieDbApi
import com.nfragiskatos.rewind.data.remote.dto.MovieDto
import com.nfragiskatos.rewind.data.remote.dto.MoviePagedResultsDto
import com.nfragiskatos.rewind.domain.model.Movie
import com.nfragiskatos.rewind.domain.repository.MovieRepository
import com.nfragiskatos.rewind.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: TheMovieDbApi
) : MovieRepository {

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
            }
        }
        emit(Resource.Loading(false))
    }
}