package com.nfragiskatos.rewind.data.mapper

import com.nfragiskatos.rewind.data.remote.dto.TvShowDto
import com.nfragiskatos.rewind.domain.model.TvShow

fun TvShowDto.toTvShow(): TvShow {
    return TvShow(
        adult = adult,
        backdropPath = backdropPath ?: "",
        firstAirDate = firstAirDate,
        genreIds = genreIds,
        id = id,
        name = name,
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalName = originalName,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath ?: "",
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}