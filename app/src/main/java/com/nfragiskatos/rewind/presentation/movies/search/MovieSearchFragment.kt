package com.nfragiskatos.rewind.presentation.movies.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.search.SearchView
import com.nfragiskatos.rewind.R
import com.nfragiskatos.rewind.databinding.FragmentMovieSearchBinding
import com.nfragiskatos.rewind.domain.model.Movie
import com.nfragiskatos.rewind.presentation.movies.popular.PopularMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieSearchFragment : Fragment() {

    private val viewModel: MovieSearchViewModel by viewModels()
    private lateinit var binding: FragmentMovieSearchBinding
    private val adapter: PopularMoviesAdapter =
        PopularMoviesAdapter(this::navigateToDetails, this::addMovie)

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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.testFlow.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
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
                    viewModel.updateSearchCriteria(it.toString())
                    binding.movieSearchRecyclerView.scrollToPosition(0)
                }
            }
            false
        }
    }

    private fun navigateToDetails(movie: Movie) {
        val bundle = bundleOf("id" to movie.id, "title" to movie.title)
        findNavController().navigate(R.id.action_movieSearchFragment_to_movieDetailFragment, bundle)
    }

    private fun addMovie(movie: Movie) {
        viewModel.addMovieToWatchedHistory(movie)
    }
}