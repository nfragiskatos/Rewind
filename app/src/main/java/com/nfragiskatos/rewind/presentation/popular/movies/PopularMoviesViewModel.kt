package com.nfragiskatos.rewind.presentation.popular.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nfragiskatos.rewind.data.mapper.toMovie
import com.nfragiskatos.rewind.data.remote.TheMovieDbApi
import com.nfragiskatos.rewind.data.remote.dto.MovieDto
import com.nfragiskatos.rewind.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val api: TheMovieDbApi
) : ViewModel() {

    private val _movie = MutableLiveData<String>("empty")
    val movie: LiveData<String> = _movie

    private val _movies = MutableLiveData<List<Movie>>(listOf())
    val movies: LiveData<List<Movie>> = _movies

    fun getPopularMovies() {
        viewModelScope.launch {
            val resp = api.getTrendingMoviesForWeek()

            if (resp.isSuccessful) {
                val body = resp.body()
                val map: List<Movie>? = body?.results?.map(MovieDto::toMovie)
                map?.let {
                    _movies.value = it
                }
            } else {
                Log.d("API CALL", "ERROR MAKING API CALL")
            }
        }
    }
}