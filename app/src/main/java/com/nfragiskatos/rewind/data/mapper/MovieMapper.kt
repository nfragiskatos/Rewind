package com.nfragiskatos.rewind.data.mapper

import com.nfragiskatos.rewind.data.remote.dto.MovieDto
import com.nfragiskatos.rewind.domain.model.Movie

fun MovieDto.toMovie(): Movie {
    return Movie(
        adult = adult,
        backdropPath = backdropPath ?: "",
        genreIds = genreIds,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}