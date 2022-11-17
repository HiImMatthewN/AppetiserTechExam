package com.nantesmatthew.movie.presentation

import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import java.util.concurrent.TimeUnit

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
        private const val PREVIEW_VIDEO_TIME = "PreviewVideoTime"
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

        //Plays Preview Video
        binder.btnPlay.setOnClickListener {
            val isVideoViewPlaying = binder.videoViewMoviePreview.isPlaying
            binder.btnPlay.playAnimation()
            if (isVideoViewPlaying) {
                movieDetailsViewModel.playPausePreview(MovieDetailsPreviewState.NotPlaying)
            } else {
                val moviePreviewUrl = movieDetailsViewModel.movie.value?.previewUrl
                movieDetailsViewModel.playPausePreview(
                    MovieDetailsPreviewState.Playing(
                        moviePreviewUrl
                    )
                )
            }

        }

        //Returns to Movie Fragment
        binder.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        //Listener on video finished playing
        binder.videoViewMoviePreview.setOnCompletionListener {
            binder.btnPlay.showStart()
            movieDetailsViewModel.playPausePreview(MovieDetailsPreviewState.NotPlaying)
        }

        lifecycleScope.launchWhenStarted {
            movieDetailsViewModel.previewVideoState.collect { moviePreviewState ->
                when (moviePreviewState) {
                    MovieDetailsPreviewState.NotPlaying -> {
                        movieDetailsViewModel.startStopTimer(false)
                        binder.videoViewMoviePreview.stopPlayback()
                        binder.videoViewMoviePreview.setVideoURI(null)
                        binder.containerVideoView.isVisible = false
                        binder.tvVideoTimeStamp.text =
                            requireContext().resources.getString(R.string.timestamp_default)

                        savedInstanceState?.putInt(PREVIEW_VIDEO_TIME, -1)
                    }
                    is MovieDetailsPreviewState.Playing -> {
                        movieDetailsViewModel.startStopTimer(true)
                        binder.root.transitionToStart()
                        binder.containerVideoView.isVisible = true
                        val moviePreviewUri = Uri.parse(moviePreviewState.previewUrl)
                        binder.videoViewMoviePreview.setVideoURI(moviePreviewUri)
                        binder.videoViewMoviePreview.start()

                        //Restore State if necessary
                        savedInstanceState?.let { state ->
                            val lastPlayed = state.getInt(PREVIEW_VIDEO_TIME)
                            if (lastPlayed != -1) {
                                binder.videoViewMoviePreview.seekTo(lastPlayed * 1000)
                                binder.btnPlay.showEnd()
                            }

                        }
                    }
                }
            }


        }


        //UI State of Movie Details
        movieDetailsViewModel.movie.observe(viewLifecycleOwner) { movie ->
            Glide.with(requireContext())
                .asDrawable()
                .load(movie.artwork)
                .apply(RequestOptions.centerCropTransform())
                .into(binder.imageCover)

            binder.tvMovieName.text = movie.trackName
            binder.tvGenre.text = movie.primaryGenre

            binder.tvRuntime.text = requireContext().resources.getString(
                R.string.runtime_placeholder,
                movie.getRuntime()
            )
            binder.tvDescription.text = movie.longDescription

            binder.btnFavorite.setImageDrawable(
                if (movie.isFavorite)
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_filled)
                else
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border)
            )

            lifecycleScope.launchWhenStarted {
                movieDetailsViewModel.previewVideoTimeState.collect {
                    binder.tvVideoTimeStamp.text = timeUnitToFullTime(it.toLong(), TimeUnit.SECONDS)
                }
            }


        }


    }

    private fun timeUnitToFullTime(time: Long, timeUnit: TimeUnit): String {
        val minute: Long = timeUnit.toMinutes(time) % 60
        val second: Long = timeUnit.toSeconds(time) % 60
        return if (minute > 0) {
            String.format("0%d:%02d", minute, second)
        } else {
            String.format("00:%02d", second)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)


        //Save Preview Video Position on Screen Rotation
        if (movieDetailsViewModel.previewVideoState.value is MovieDetailsPreviewState.Playing) {
            val currentPosition = movieDetailsViewModel.previewVideoTimeState.value
            outState.putInt(PREVIEW_VIDEO_TIME, currentPosition)
        }
    }

    override fun onPause() {
        super.onPause()

        //Save User Session
        movieDetailsViewModel.saveUserSession(UserSession(Date(), Screen.Detail, args.trackId))

    }


}