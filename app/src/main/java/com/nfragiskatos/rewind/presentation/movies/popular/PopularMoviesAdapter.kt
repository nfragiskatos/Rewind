package com.nfragiskatos.rewind.presentation.movies.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nfragiskatos.rewind.BuildConfig
import com.nfragiskatos.rewind.R
import com.nfragiskatos.rewind.domain.model.Movie

class PopularMoviesAdapter(
    private val onClick: (Movie) -> Unit = { _ -> },
    private val onAdd: (Movie) -> Unit = { _ -> }
) :
    ListAdapter<Movie, PopularMoviesAdapter.MovieViewHolder>(MovieDiffCallback) {

    class MovieViewHolder(
        itemView: View,
        private val onClick: (Movie) -> Unit = { _ -> },
        private val onAdd: (Movie) -> Unit = { _ -> }
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.text_movie_name)
        private val poster: ImageView = itemView.findViewById(R.id.image_movie_poster)
        private var addButton: Button = itemView.findViewById(R.id.add_movie_button)
        private var currentMovie: Movie? = null


        fun bind(movie: Movie) {
            currentMovie = movie
            title.text = movie.title
            title.setOnClickListener { onClick(movie) }
            addButton.setOnClickListener { onAdd(movie) }
            Glide.with(itemView.context)
                .load("${BuildConfig.THE_MOVIE_DB_API_IMAGE_BASE_URL}${movie.backdropPath}")

                .into(poster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, onClick, onAdd)
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