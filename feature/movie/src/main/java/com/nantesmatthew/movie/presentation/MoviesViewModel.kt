package com.nantesmatthew.movie.presentation

import android.util.Log
import androidx.lifecycle.*
import com.nantesmatthew.core.util.ConnectivityObserver
import com.nantesmatthew.core.util.ConnectivityStatus
import com.nantesmatthew.core.util.Status
import com.nantesmatthew.movie.domain.model.Movie
import com.nantesmatthew.movie.domain.model.MoviesByGenre
import com.nantesmatthew.movie.domain.use_case.AddRemoveFromFavoritesUseCase
import com.nantesmatthew.movie.domain.use_case.FilterMovieUseCase
import com.nantesmatthew.movie.domain.use_case.GetMovieUseCase
import com.nantesmatthew.movie.domain.use_case.SortByGenreUseCase
import com.nantesmatthew.user_session.domain.model.UserSession
import com.nantesmatthew.user_session.domain.use_case.GetLastUserSessionUseCase
import com.nantesmatthew.user_session.domain.use_case.SaveUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val sortByGenreUseCase: SortByGenreUseCase,
    private val filterMovieUseCase: FilterMovieUseCase,
    private val getMovieUseCase: GetMovieUseCase,
    private val addRemoveFromFavoritesUseCase: AddRemoveFromFavoritesUseCase,
    private val saveUserSessionUseCase: SaveUserSessionUseCase,
    private val getLastUserSessionUseCase: GetLastUserSessionUseCase,
    private val connectivityObserver: ConnectivityObserver,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val TAG = "MoviesViewModel"
        const val MOVIE_LIST = "MovieList"
        private const val SEARCHBAR_EXPANDED = "SearchBarExpanded"

    }

    private val _stateSearchView = MutableStateFlow(stateHandle[SEARCHBAR_EXPANDED] ?: false)
    val stateSearchView get() = _stateSearchView.asStateFlow()

    val networkState = connectivityObserver.observe().stateIn(
        viewModelScope, SharingStarted.Lazily, ConnectivityStatus.Unavailable
    )

    private val _movieGenres: MutableLiveData<List<MoviesByGenre>> =
        stateHandle.getLiveData(MOVIE_LIST)
    val movieGenres get() = _movieGenres as LiveData<List<MoviesByGenre>>


    private val _searchQuery = MutableStateFlow("")
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())


    init {
        combine(_movies, _favoriteMovies, _searchQuery) { movies, faveMovies, searchQuery ->

            val moviesWithUserFave = (faveMovies + movies).distinctBy { it.trackId }
            val filterMovies = filterMovieUseCase(moviesWithUserFave, searchQuery)
            val moviesByGenre = sortByGenreUseCase(filterMovies)

            stateHandle[MOVIE_LIST] = moviesByGenre
            _movieGenres.postValue(moviesByGenre)
        }.launchIn(viewModelScope)

        fetchData()

    }

    fun fetchData() {
        getMovies()
        getFavorites()
    }

    fun expandSearchView(expand: Boolean) {
        _stateSearchView.value = expand
        stateHandle[SEARCHBAR_EXPANDED] = expand



    }

    fun filterMovie(filterQuery: String) {
        _searchQuery.value = filterQuery
    }

    private fun getMovies() {
        viewModelScope.launch {

            //Query are fix in accordance to Exam Instructions
            getMovieUseCase(
                term = "star",
                country = "au",
                media = "all"
            ).collectLatest {
                _movies.value = it.data ?: emptyList()

            }

        }

    }

    private fun getFavorites() {
        viewModelScope.launch {
            getMovieUseCase.getFavorites().collectLatest {
                _favoriteMovies.value = it
            }
        }

    }

    fun saveUserSession(userSession: UserSession) {
        viewModelScope.launch {
            saveUserSessionUseCase(userSession)
        }
    }

    fun addRemoveFromFavorites(movie: Movie) {
        viewModelScope.launch {
            addRemoveFromFavoritesUseCase(movie)

        }
    }

    fun getLastUserSession(cb: ((userSession: UserSession) -> Unit)) {
        viewModelScope.launch {
            val getUserSessionResult = getLastUserSessionUseCase()

            if (getUserSessionResult.status == Status.SUCCESS) {
                val userSession = getUserSessionResult.data
                userSession?.apply {
                    cb.invoke(userSession)
                }

            }
        }


    }

}