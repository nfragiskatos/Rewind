package com.nfragiskatos.rewind.domain.repository

import com.nfragiskatos.rewind.domain.model.Movie
import com.nfragiskatos.rewind.domain.model.MovieDetails
import com.nfragiskatos.rewind.domain.model.MoviePage
import com.nfragiskatos.rewind.util.Resource
import com.nfragiskatos.rewind.util.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun searchMovies(searchTerm: String): Flow<Resource<List<Movie>>>

    fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>>

    suspend fun addMovieToWatchedHistory(movie: Movie): Movie

    suspend fun removeMovieFromWatchedHistory(movie: Movie): Movie

    suspend fun searchMoviesPagingTest(
        searchTerm: String,
        page: Int
    ): Result<MoviePage, Throwable>
}