package com.nfragiskatos.rewind.presentation.movies.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.nfragiskatos.rewind.domain.model.Movie
import com.nfragiskatos.rewind.domain.repository.MoviePagingSource
import com.nfragiskatos.rewind.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val query = MutableStateFlow("")

    private val _movieUpdate = MutableLiveData<MovieUpdate?>(null)
    val movieUpdate: LiveData<MovieUpdate?> = _movieUpdate

    private val _movies = MutableLiveData<List<Movie>>(listOf())
    val movies: LiveData<List<Movie>> = _movies

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val testFlow = query.flatMapLatest { query ->
        Pager(PagingConfig(pageSize = 20)) {
            MoviePagingSource(
                movieRepository,
                query
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun updateSearchCriteria(searchTerm: String) {
        query.value = searchTerm
    }

    fun addToWatchedHistory(movie: Movie, position: Int) {
        viewModelScope.launch {
            val updated = movieRepository.addMovieToWatchedHistory(movie)
            _movieUpdate.value = MovieUpdate(updated, position)
        }
    }

    fun removeFromWatchedHistory(movie: Movie, position: Int) {
        viewModelScope.launch {
            val updated = movieRepository.removeMovieFromWatchedHistory(movie)
            _movieUpdate.value = MovieUpdate(updated, position)
        }
    }
}