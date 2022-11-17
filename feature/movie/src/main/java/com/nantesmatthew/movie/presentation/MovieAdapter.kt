package com.nantesmatthew.movie.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nantesmatthew.core.ext.*
import com.nantesmatthew.movie.R
import com.nantesmatthew.movie.databinding.ItemMovieBinding
import com.nantesmatthew.movie.domain.model.Movie

class MovieAdapter(
    val onMovieSelected: ((movie: Movie, imageView: ImageView) -> Unit)? = null,
    val onAddToFavorite: ((movie: Movie) -> Unit)? = null
) :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        val binder = ItemMovieBinding.bind(view)
        return MovieViewHolder(binder)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (holder.binder.tvTrackName.text == movie.trackName) return

        Glide.with(holder.itemView.context).load(movie.artwork)
            .placeholder(R.drawable.ic_image_loading_placeholder)
            .error(R.drawable.ic_image_error_placeholder)
            .centerCrop()
            .into(holder.binder.imageCover)
        holder.binder.tvTrackName.text = movie.trackName
        holder.binder.tvArtistName.text = movie.artistName
        holder.binder.tvTicketPrice.text = "$${movie.trackPrice}"
        holder.binder.btnFavorite.setImageDrawable(
            if (movie.isFavorite)
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_favorite_filled)
            else
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_favorite_border)
        )
        holder.binder.tvShortDescription.text = movie.shortDescription
        holder.bind(movie)
    }

    inner class MovieViewHolder(val binder: ItemMovieBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(movie: Movie) {
            binder.tvTrackName.touchListener(
                onTouchDown = {
                    itemView.animate(R.animator.push_down)

                },
                onTouchUp = {
                    itemView.animate(R.animator.push_up).doOnEnd {
                        onMovieSelected?.invoke(movie, binder.imageCover.apply {
                            transitionName = movie.artwork
                        })
                    }

                }
            )
            binder.imageCover.touchListener(
                onTouchDown = {
                    itemView.animate(R.animator.push_down)

                },
                onTouchUp = {
                    itemView.animate(R.animator.push_up).doOnEnd {
                        onMovieSelected?.invoke(movie, binder.imageCover.apply {
                            transitionName = movie.artwork
                        })
                    }

                }
            )

            binder.btnSeeDescription.setOnClickListener {
                val notExpanded = binder.btnSeeDescription.rotation == 0.0f

                if (notExpanded) {
                    binder.btnSeeDescription.animate(R.animator.rotate_0_to_90)
                    binder.tvShortDescription.expand()
                } else {
                    binder.btnSeeDescription.animate(R.animator.rotate_90_to_0)
                    binder.tvShortDescription.collapse()
                }
            }

            binder.btnFavorite.setOnClickListener {
                onAddToFavorite?.invoke(movie)
            }

        }

    }

    companion object {
        private const val TAG = "MovieAdapter"
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.trackId == newItem.trackId
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}

