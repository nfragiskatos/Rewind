package com.nfragiskatos.rewind.data.remote

import com.nfragiskatos.rewind.BuildConfig
import com.nfragiskatos.rewind.data.remote.dto.MoviePagedResultsDto
import com.nfragiskatos.rewind.data.remote.dto.TvShowPagedResultsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbApi {

    @GET("trending/movie/week")
    suspend fun getTrendingMoviesForWeek(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DB_API_KEY
    ): MoviePagedResultsDto

    @GET("search/tv")
    suspend fun searchTvShowsByQuery(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DB_API_KEY,
        @Query("query") query: String
    ): TvShowPagedResultsDto

}