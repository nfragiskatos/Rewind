package com.nfragiskatos.rewind.presentation.popular.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.rewind.R
import com.nfragiskatos.rewind.domain.model.Movie

class PopularMoviesAdapter :
    ListAdapter<Movie, PopularMoviesAdapter.MovieViewHolder>(MovieDiffCallback) {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textview: TextView = itemView.findViewById(R.id.movie_name)
        private var currentMovie: Movie? = null

        fun bind(movie: Movie) {
            currentMovie = movie

            textview.text = movie.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
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