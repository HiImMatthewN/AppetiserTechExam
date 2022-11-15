package com.nantesmatthew.movie.domain.repository

import com.nantesmatthew.core.util.Resource
import com.nantesmatthew.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow


/**
 * Repository for movie
 */
interface MovieRepository {


    suspend fun getMovies(term: String, country: String, media: String): Flow<Resource<List<Movie>>>

    suspend fun getMovie(trackId: Int): Resource<Movie>

    fun getFavoriteMovies():Flow<List<Movie>>

    suspend fun addToFavorites(movie: Movie): Resource<Unit>

    suspend fun removeToFavorites(movie: Movie): Resource<Unit>
}