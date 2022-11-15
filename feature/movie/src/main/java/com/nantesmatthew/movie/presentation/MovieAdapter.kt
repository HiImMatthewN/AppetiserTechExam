package com.nantesmatthew.movie.presentation

import android.animation.Animator
import android.animation.ValueAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

        Glide.with(holder.itemView.context).load(movie.artwork).centerCrop()
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
            binder.tvShortDescription.isVisible = false
            binder.imageCover.setOnClickListener {
                onMovieSelected?.invoke(movie, binder.imageCover.apply {
                    transitionName = movie.artwork
                })
            }
            binder.btnSeeDescription.setOnClickListener {
                val notExpanded = binder.btnSeeDescription.rotation == 0.0f
                binder.btnSeeDescription.animate().rotation(if (notExpanded) 90.0f else 0.0f)
                    .setDuration(300).start()

                binder.tvShortDescription.animateVisibility(notExpanded)

            }

            binder.btnFavorite.setOnClickListener {
                onAddToFavorite?.invoke(movie)
            }

        }
    }

    companion object {
        const val TAG = "MovieAdapter"

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

fun View.animateVisibility(setVisible: Boolean) {
    if (setVisible) expand(this) else collapse(this)
}

private fun expand(view: View) {
    view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val initialHeight = 0
    val targetHeight = view.measuredHeight

    // Older versions of Android (pre API 21) cancel animations for views with a height of 0.
    //v.getLayoutParams().height = 1;
    view.layoutParams.height = 0
    view.isVisible = true

    animateView(view, initialHeight, targetHeight) {

    }
}

private fun collapse(view: View) {
    val initialHeight = view.measuredHeight
    val targetHeight = 0

    animateView(view, initialHeight, targetHeight) {
        view.isVisible = false
    }
}

private fun animateView(v: View, initialHeight: Int, targetHeight: Int, onEnd: () -> Unit) {
    val valueAnimator = ValueAnimator.ofInt(initialHeight, targetHeight)
    valueAnimator.addUpdateListener { animation ->
        v.layoutParams.height = animation.animatedValue as Int
        v.requestLayout()
    }
    valueAnimator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationEnd(animation: Animator) {
            v.layoutParams.height = targetHeight
            onEnd()
        }

        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationCancel(animation: Animator) {
        }

        override fun onAnimationRepeat(animation: Animator) {}
    })
    valueAnimator.duration = 300
    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.start()
}