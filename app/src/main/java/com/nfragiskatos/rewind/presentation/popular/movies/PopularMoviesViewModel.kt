package com.nfragiskatos.rewind.presentation.popular.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nfragiskatos.rewind.data.mapper.toMovie
import com.nfragiskatos.rewind.data.remote.TheMovieDbApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val api: TheMovieDbApi
) : ViewModel() {

    private val _movie = MutableLiveData<String>("empty")
    val movie: LiveData<String> = _movie

    fun getPopularMovies() {
        viewModelScope.launch {
            val resp = api.getTrendingMoviesForWeek()

            if (resp.isSuccessful) {
                val body = resp.body()
                body?.results?.forEach { movie ->
                    Log.d("API CALL", "Movies: ${movie.title}, Release: ${movie.releaseDate}")
                }
                _movie.value = body?.results?.first()?.toMovie()?.title ?: "error"
            } else {
                Log.d("API CALL", "ERROR MAKING API CALL")
            }
        }
    }
}