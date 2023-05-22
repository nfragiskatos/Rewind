package com.nfragiskatos.rewind.presentation.movies.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.nfragiskatos.rewind.BuildConfig
import com.nfragiskatos.rewind.R
import com.nfragiskatos.rewind.domain.model.Movie

class PopularMoviesAdapter(
    private val onClick: (Movie) -> Unit = { _ -> },
    private val onAddOrRemove: (Movie, Int) -> Unit = { _, _ -> }
) :
    PagingDataAdapter<Movie, PopularMoviesAdapter.MovieViewHolder>(MovieDiffCallback) {

    class MovieViewHolder(
        itemView: View,
        private val onClick: (Movie) -> Unit = { _ -> },
        private val onAddOrRemove: (Movie, Int) -> Unit = { _, _ -> }
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.text_movie_name)
        private val poster: ImageView = itemView.findViewById(R.id.image_movie_poster)
        private var addButton: MaterialButton = itemView.findViewById(R.id.add_movie_button)
        private var currentMovie: Movie? = null
        private var progressBar: LinearProgressIndicator =
            itemView.findViewById(R.id.movie_item_progress_bar)


        fun bind(movie: Movie?) {
            currentMovie = movie
            title.text = movie?.title ?: "NULL VALUE"
            if (movie != null) {
                progressBar.hide()
                title.setOnClickListener { onClick(movie) }
                addButton.isEnabled = true
                addButton.setOnClickListener {
                    progressBar.show()
                    addButton.isEnabled = false
                    onAddOrRemove(movie, bindingAdapterPosition)
                }
                Glide.with(itemView.context)
                    .load("${BuildConfig.THE_MOVIE_DB_API_IMAGE_BASE_URL}${movie.backdropPath}")
                    .into(poster)

                if (movie.dateWatched != null) {
                    addButton.setIconResource(R.drawable.baseline_remove_24)
                    addButton.text = "Remove from Watched"
                } else {
                    addButton.setIconResource(R.drawable.baseline_add_24)
                    addButton.text = "Add to Watched"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, onClick, onAddOrRemove)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }
}

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}