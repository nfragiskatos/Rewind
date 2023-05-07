package com.nfragiskatos.rewind.domain.repository

import com.nfragiskatos.rewind.domain.model.Movie
import com.nfragiskatos.rewind.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun searchMovies(searchTerm: String): Flow<Resource<List<Movie>>>
}