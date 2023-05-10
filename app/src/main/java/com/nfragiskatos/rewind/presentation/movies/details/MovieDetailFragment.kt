package com.nfragiskatos.rewind.presentation.movies.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.nfragiskatos.rewind.BuildConfig
import com.nfragiskatos.rewind.R
import com.nfragiskatos.rewind.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupObservers()

        val id = arguments?.getInt("id")
        val title = arguments?.getString("title")

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            this.title = "Details - $title" ?: "Details"
        }

        id?.let {
            viewModel.findMovieDetails(it)
        }


        return binding.root
    }

    private fun setupObservers() {
        viewModel.movie.observe(viewLifecycleOwner) { details ->
            details?.let {
                binding.movieDetailTitle.text = details.title
                binding.movieDetailSummary.text = details.overview
                Glide.with(this)
                    .load("${BuildConfig.THE_MOVIE_DB_API_IMAGE_BASE_URL}${details.backdropPath}")
                    .into(binding.movieDetailsHeroImage)
            }
        }
    }
}