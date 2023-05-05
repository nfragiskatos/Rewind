package com.nfragiskatos.rewind.presentation.search.movies

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val api: TheMovieDbApi
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>(listOf())
    val movies: LiveData<List<Movie>> = _movies


    fun searchMovies(searchTerm: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resp = api.searchMoviesByQuery(query = searchTerm)

            if (resp.isSuccessful) {
                withContext(Dispatchers.Main) {
                    val body = resp.body()
                    val map: List<Movie>? = body?.results?.map(MovieDto::toMovie)
                    map?.let {
                        _movies.value = it
                    }
                }
            } else {
                Log.d("API CALL", "ERROR MAKING API CALL")
            }
        }
    }
}