package com.nantesmatthew.movie.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.nantesmatthew.movie.R
import com.nantesmatthew.movie.databinding.ItemGenreBinding
import com.nantesmatthew.movie.domain.model.Movie
import com.nantesmatthew.movie.domain.model.MoviesByGenre

class GenreAdapter : ListAdapter<MoviesByGenre,GenreAdapter.GenreViewHolder>(DIFF_UTIL) {
    var onMovieSelected: ((movie: Movie,imageView:ImageView) -> Unit)? = null
    var onAddToFavorite:((movie:Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        val binder = ItemGenreBinding.bind(view)
        return GenreViewHolder(binder)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val movieGenre = getItem(position)
        holder.binder.tvGenre.text = movieGenre.genre
        holder.binder.rvMovies.adapter = MovieAdapter(this.onMovieSelected,this.onAddToFavorite).apply {
            submitList(movieGenre.movies)

        }
    }


    inner class GenreViewHolder(val binder: ItemGenreBinding) : RecyclerView.ViewHolder(binder.root)

    companion object {
        private const val TAG = "GenreAdapter"
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MoviesByGenre>() {
            override fun areItemsTheSame(oldItem: MoviesByGenre, newItem: MoviesByGenre): Boolean {
                return oldItem.genre == newItem.genre
            }

            override fun areContentsTheSame(
                oldItem: MoviesByGenre,
                newItem: MoviesByGenre
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}