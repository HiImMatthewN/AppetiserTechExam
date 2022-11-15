package com.nantesmatthew.movie.presentation

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nantesmatthew.movie.R
import com.nantesmatthew.movie.databinding.FragmentMovieDetailsBinding
import com.nantesmatthew.user_session.domain.model.Screen
import com.nantesmatthew.user_session.domain.model.UserSession
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 * Screen for showing movie details selected from [MoviesFragment]
 *
 *
 */
@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var binder: FragmentMovieDetailsBinding
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val movieDetailsViewModel by viewModels<MovieDetailViewModel>()

    companion object {
        private const val TAG = "MovieDetailsFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binder = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Showing of image from SharedElement Transition
        args.imageCover?.let {
            val imageView = binder.imageCover.apply {
                transitionName = args.imageCover
            }
            Glide.with(requireContext())
                .asDrawable()
                .load(args.imageCover)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView)
        }

        //Handles on click of favorite
        binder.btnFavorite.setOnClickListener {
            movieDetailsViewModel.addRemoveFromFavorite(movieDetailsViewModel.movie.value)
        }
        //Returns to Movie Fragment
        binder.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        //UI State of Movie Details
        movieDetailsViewModel.movie.observe(viewLifecycleOwner) { movie ->
            if (binder.imageCover.drawable == null)
            Glide.with(requireContext())
                .asDrawable()
                .load(movie.artwork)
                .apply(RequestOptions.centerCropTransform())
                .into(binder.imageCover)

            binder.tvMovieName.text = movie.trackName
            binder.tvGenre.text = movie.primaryGenre

            binder.tvRuntime.text = "Runtime: ${movie.getRuntime()}"
            binder.tvDescription.text = movie.longDescription


            binder.btnFavorite.setImageDrawable(
                if (movie.isFavorite)
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_filled)
                else
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border)

            )
        }


    }

    override fun onPause() {
        super.onPause()

        //Save User Session
        movieDetailsViewModel.saveUserSession(UserSession(Date(), Screen.Detail, args.trackId))

    }


}