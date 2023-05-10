package com.nfragiskatos.rewind.data.remote.dto.media.movie

import com.google.gson.annotations.SerializedName
import com.nfragiskatos.rewind.data.remote.dto.*
import java.util.*

data class MovieDetailsDto(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String? = "",
    @SerializedName("belongs_to_collection")
    val collection: Collection,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String? = "",
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("release_date")
    val releaseDate: Date,
    val revenue: Int,
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)