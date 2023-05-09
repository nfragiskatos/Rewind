package com.nfragiskatos.rewind.presentation.movies.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nfragiskatos.rewind.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    fun findMovieDetails(id: Int) {
        viewModelScope.launch {
            repository.getMovieDetails(id).collect()
        }
    }
}