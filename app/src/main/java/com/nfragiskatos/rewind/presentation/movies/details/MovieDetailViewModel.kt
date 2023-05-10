package com.nfragiskatos.rewind.presentation.movies.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nfragiskatos.rewind.domain.model.MovieDetails
import com.nfragiskatos.rewind.domain.repository.MovieRepository
import com.nfragiskatos.rewind.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movie = MutableLiveData<MovieDetails>()
    val movie: LiveData<MovieDetails> = _movie

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun findMovieDetails(id: Int) {
        viewModelScope.launch {
            repository.getMovieDetails(id)
                .collect { result ->

                    when (result) {
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            _isLoading.value = result.isLoading
                        }
                        is Resource.Success -> {
                            _movie.value = result.data!!
                        }
                    }

                }
        }
    }
}