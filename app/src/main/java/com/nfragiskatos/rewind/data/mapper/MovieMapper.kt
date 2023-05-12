package com.nfragiskatos.rewind.data.mapper

import com.nfragiskatos.rewind.data.local.entity.media.movie.MovieEntity
import com.nfragiskatos.rewind.data.remote.dto.media.movie.MovieDetailsDto
import com.nfragiskatos.rewind.data.remote.dto.media.movie.MovieDto
import com.nfragiskatos.rewind.domain.model.Movie
import com.nfragiskatos.rewind.domain.model.MovieDetails

fun MovieDto.toMovie(): Movie {
    return Movie(
        backdropPath = backdropPath ?: "",
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

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        backdropPath = backdropPath ?: "",
        id = id,
        overview = overview,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate,
        title = title
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        backdropPath = backdropPath ?: "",
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

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        backdropPath = backdropPath ?: "",
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