package com.nfragiskatos.rewind.data.remote

import com.nfragiskatos.rewind.BuildConfig
import com.nfragiskatos.rewind.data.remote.dto.media.movie.MovieDetailsDto
import com.nfragiskatos.rewind.data.remote.dto.media.movie.MoviePagedResultsDto
import com.nfragiskatos.rewind.data.remote.dto.media.tvshow.TvShowPagedResultsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {

    @GET("trending/movie/week")
    suspend fun getTrendingMoviesForWeek(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DB_API_KEY
    ): Response<MoviePagedResultsDto>

    @GET("search/tv")
    suspend fun searchTvShowsByQuery(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DB_API_KEY,
        @Query("query") query: String
    ): Response<TvShowPagedResultsDto>

    @GET("search/movie")
    suspend fun searchMoviesByQuery(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DB_API_KEY,
        @Query("query") query: String
    ): Response<MoviePagedResultsDto>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DB_API_KEY,
    ): Response<MovieDetailsDto>
}