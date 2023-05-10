package com.nfragiskatos.rewind.data.remote.dto.media.movie

import com.google.gson.annotations.SerializedName

data class MoviePagedResultsDto(
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    val page: Int,
    val results: List<MovieDto>
)
