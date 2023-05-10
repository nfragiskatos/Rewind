package com.nfragiskatos.rewind.data.remote.dto.media.movie

import com.google.gson.annotations.SerializedName

data class Collection(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val id: Int,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String
)