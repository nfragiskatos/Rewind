package com.nfragiskatos.rewind.domain.repository

import com.nfragiskatos.rewind.data.remote.dto.media.movie.MoviePagedResultsDto
import com.nfragiskatos.rewind.domain.model.Movie
import com.nfragiskatos.rewind.domain.model.MovieDetails
import com.nfragiskatos.rewind.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepository {

    fun searchMovies(searchTerm: String): Flow<Resource<List<Movie>>>

    suspend fun searchMoviesPaging(searchTerm: String, page: Int): Response<MoviePagedResultsDto>

    fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>>

    fun addMovieToWatchedHistory(movie: Movie): Flow<Resource<Movie>>
}