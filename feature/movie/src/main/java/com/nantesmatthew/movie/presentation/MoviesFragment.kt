package com.nantesmatthew.movie.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nantesmatthew.core.ext.animationListener
import com.nantesmatthew.core.ext.reStoreState
import com.nantesmatthew.movie.R
import com.nantesmatthew.movie.databinding.FragmentMoviesBinding
import com.nantesmatthew.user_session.domain.model.Screen
import com.nantesmatthew.user_session.domain.model.UserSession
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList


/**
 *
 *  The Main Entry Point of the App
 *  ```
 *  Shows Available and User Liked Movies list in grid layout is categorized by genre
 *
 *
 */

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private lateinit var binder: FragmentMoviesBinding
    private val viewModelMovies by viewModels<MoviesViewModel>()

    companion object {
        private const val TAG = "MoviesFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentMoviesBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Open/Close/Clearing Text of SearchView OnClick
        binder.btnSearch.setOnClickListener {

            //If SearchView has text, clear it.
            if (binder.etSearchView.text.isNotBlank()) {
                binder.etSearchView.text.clear()
            } else {
                viewModelMovies.expandSearchView(!viewModelMovies.stateSearchView.value)
            }
        }

        val genreAdapter = GenreAdapter()
        //Handles Movie Image on Click
        genreAdapter.onMovieSelected = { movie, imageView ->

            val extras = FragmentNavigatorExtras(imageView to movie.artwork)
            val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                movie.trackId,
                movie.artwork
            )
            findNavController().navigate(action, extras)

        }
        //Handles Movie Favorite on Click
        genreAdapter.onAddToFavorite = { movie ->
            viewModelMovies.addRemoveFromFavorites(movie)
        }

        //Callback on searchview query change
        binder.etSearchView.doOnTextChanged { text, _, _, _ ->
            viewModelMovies.filterMovie(text.toString())
        }

        //Current State of SearchView
        lifecycleScope.launchWhenStarted {
            viewModelMovies.stateSearchView.collect { expand ->
                if (expand) {
                    binder.etSearchView.isEnabled = true
                    binder.btnSearch.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_close_24
                        )
                    )
                    binder.root.transitionToEnd()
                } else {
                    binder.etSearchView.isEnabled = false

                    binder.btnSearch.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_search
                        )
                    )
                    binder.root.transitionToStart()
                }
            }
        }


        //State of Movies
        viewModelMovies.movieGenres.distinctUntilChanged().observe(viewLifecycleOwner) { genres ->
            binder.noResults.root.isVisible = genres.isEmpty()
            binder.rvMovies.adapter = genreAdapter.apply {
                binder.rvMovies.reStoreState()
                submitList(genres)
            }


        }

        //Toolbar User Session Info
        viewModelMovies.getLastUserSession { userSession ->
            runToolbarAnimation(userSession)
        }


    }

    //Looping Animation for toolbar
    private var toolBarTitleRunnable: Runnable? = null
    private val handler = Handler(Looper.getMainLooper())
    private fun runToolbarAnimation(userSession: UserSession) {
        var toolbarState: ToolBarState = ToolBarState.UserSession

        val exitAnimation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_fade_out)
        val enterAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_fade_in)

        exitAnimation.animationListener(
            onStart = {
                binder.tvToolBarTitle.text = getToolbarTitle(toolbarState, userSession)
            },
            onEnd = {
                enterAnimation.animationListener(
                    onStart = {
                        toolbarState =
                            if (toolbarState is ToolBarState.Title) ToolBarState.UserSession else ToolBarState.Title
                        binder.tvToolBarTitle.text = getToolbarTitle(toolbarState, userSession)
                    },
                    onEnd = {


                    })

                binder.tvToolBarTitle.startAnimation(enterAnimation)
            })

        toolBarTitleRunnable = Runnable {
            if (toolBarTitleRunnable != null) {
                binder.tvToolBarTitle.startAnimation(exitAnimation)
                handler.postDelayed(toolBarTitleRunnable!!, 10000)
            }
        }
        handler.post(toolBarTitleRunnable!!)

    }

    private fun getToolbarTitle(toolBarState: ToolBarState, userSession: UserSession): String {
        return when (toolBarState) {
            ToolBarState.Title -> requireContext().resources.getString(R.string.appetiser_apps)
            ToolBarState.UserSession -> requireContext().resources.getString(
                R.string.last_opened,
                userSession.getLastOpened()
            )
        }
    }

    override fun onPause() {
        super.onPause()
        //Save UserSession
        viewModelMovies.saveUserSession(UserSession(Date(), Screen.Category, 0))
    }

    override fun onStop() {
        super.onStop()
        //Clean up Toolbar Runnable
        if (toolBarTitleRunnable != null) {
            handler.removeCallbacks(toolBarTitleRunnable!!)
            toolBarTitleRunnable = null
        }

    }


}


