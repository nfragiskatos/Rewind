package com.nfragiskatos.rewind.domain.model

data class MoviePage(
    val totalPages: Int,
    val totalResults: Int,
    val page: Int,
    val results: List<Movie>
) {
    fun nextPage() = if (page < totalPages) page.plus(1) else null
}
