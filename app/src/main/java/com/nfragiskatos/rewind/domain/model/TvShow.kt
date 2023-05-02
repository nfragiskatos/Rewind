package com.nfragiskatos.rewind.domain.model

import java.util.*

data class TvShow(
    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: Date?,
    val genreIds: List<Int>,
    val id: Int,
    val name: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int
)