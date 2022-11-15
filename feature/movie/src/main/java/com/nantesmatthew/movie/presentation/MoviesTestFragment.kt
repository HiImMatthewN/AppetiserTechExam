package com.nantesmatthew.movie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nantesmatthew.movie.databinding.FragmentMoviesTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesTestFragment : Fragment() {
    private lateinit var binder: FragmentMoviesTestBinding
    private val viewModelMovies by viewModels<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentMoviesTestBinding.inflate(inflater, container, false)
        return binder.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelMovies.movieGenres.observe(viewLifecycleOwner) { genres ->
            binder.rvMovies.adapter = GenreAdapter().apply {
                binder.rvMovies.reStoreState()
                submitList(genres)
            }

        }


    }
}