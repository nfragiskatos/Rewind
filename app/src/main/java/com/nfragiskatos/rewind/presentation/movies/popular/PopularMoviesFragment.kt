package com.nfragiskatos.rewind.presentation.movies.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nfragiskatos.rewind.R
import com.nfragiskatos.rewind.databinding.FragmentPopularMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private val viewModel: PopularMoviesViewModel by viewModels()

    private lateinit var binding: FragmentPopularMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_popular_movies, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = PopularMoviesAdapter()

        binding.popularMoviesRecyclerView.adapter = adapter

//        viewModel.movies.observe(viewLifecycleOwner) { movies ->
//            movies?.let {
//                adapter.submitList(it)
//            }
//        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getPopularMovies()
    }

}