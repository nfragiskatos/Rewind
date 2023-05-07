package com.nfragiskatos.rewind.presentation.search.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.search.SearchView
import com.nfragiskatos.rewind.R
import com.nfragiskatos.rewind.databinding.FragmentMovieSearchBinding
import com.nfragiskatos.rewind.presentation.popular.movies.PopularMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchFragment : Fragment() {

    private val viewModel: MovieSearchViewModel by viewModels()
    private lateinit var binding: FragmentMovieSearchBinding
    private val adapter: PopularMoviesAdapter = PopularMoviesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_search, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.movieSearchRecyclerView.adapter = adapter

        setupObservers()
        setupEventHandlers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                adapter.submitList(it)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.movieSearchProgressBar.show()
            } else {
                binding.movieSearchProgressBar.hide()
            }
        }
    }

    private fun setupEventHandlers() {
        val searchView: SearchView = binding.movieSearchSearchView

        searchView.editText.setOnEditorActionListener { _, _, _ ->
            val query = searchView.text
            searchView.hide()
            binding.moveSearchSearchBar.text = query

            query?.let {
                if (it.isNotBlank()) {
                    viewModel.searchMovies(it.toString())
                }
            }
            false
        }
    }
}