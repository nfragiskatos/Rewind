package com.nfragiskatos.rewind.data.remote.dto.media.tvshow

import com.google.gson.annotations.SerializedName

data class TvShowPagedResultsDto(
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    val page: Int,
    val results: List<TvShowDto>
)
