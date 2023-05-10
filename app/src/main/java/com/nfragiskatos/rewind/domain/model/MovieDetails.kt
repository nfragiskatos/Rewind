package com.nfragiskatos.rewind.domain.model

import java.util.*

data class MovieDetails(
    val backdropPath: String = "",
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: Date,
    val title: String,
)
