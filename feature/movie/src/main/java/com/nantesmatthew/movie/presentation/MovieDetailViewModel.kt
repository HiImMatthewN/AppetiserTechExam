package com.nantesmatthew.movie.presentation

import android.util.Log
import androidx.lifecycle.*
import com.nantesmatthew.core.util.Status
import com.nantesmatthew.movie.domain.model.Movie
import com.nantesmatthew.movie.domain.use_case.AddRemoveFromFavoritesUseCase
import com.nantesmatthew.movie.domain.use_case.GetMovieUseCase
import com.nantesmatthew.user_session.domain.model.UserSession
import com.nantesmatthew.user_session.domain.use_case.SaveUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val saveUserSessionUseCase: SaveUserSessionUseCase,
    private val addRemoveFromFavoritesUseCase: AddRemoveFromFavoritesUseCase,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie = _movie as LiveData<Movie>

    companion object {
        private const val TAG = "MovieDetailViewModel"
    }

    init {
        val trackId: Int = savedStateHandle["trackId"] ?: 0
        getMovie(trackId)
    }


    private fun getMovie(trackId: Int) {
        viewModelScope.launch {
            val movieResult = getMovieUseCase(trackId)
            if (movieResult.status == Status.SUCCESS) {
                movieResult.data?.let { movie ->
                    _movie.postValue(movie)
                }
            }
        }
    }

    fun addRemoveFromFavorite(movie: Movie?) {
        viewModelScope.launch {
            movie?.let {
                val updateResult = addRemoveFromFavoritesUseCase(it)
                if (updateResult.status == Status.SUCCESS)
                    _movie.postValue(updateResult.data!!)

            }
        }
    }

    fun saveUserSession(userSession: UserSession) {
        viewModelScope.launch {
           val isSaved =  saveUserSessionUseCase(userSession)
            Log.d(TAG, "saveUserSession: ${isSaved.status}")
        }
    }
}