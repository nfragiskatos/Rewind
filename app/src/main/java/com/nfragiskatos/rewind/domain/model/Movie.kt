package com.nfragiskatos.rewind.domain.model

import java.util.*

data class Movie(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: Date,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
