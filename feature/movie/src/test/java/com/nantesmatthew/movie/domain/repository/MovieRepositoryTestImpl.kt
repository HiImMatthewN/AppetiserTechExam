package com.nantesmatthew.movie.domain.repository

import com.nantesmatthew.core.util.Resource
import com.nantesmatthew.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryTestImpl : MovieRepository {
    val dummyMovieDataSource = ArrayList<Movie>()
    val dummyFaveMovieDataSource = ArrayList<Movie>()

    override suspend fun getMovies(
        term: String,
        country: String,
        media: String
    ): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.success(dummyMovieDataSource))
    }

    override suspend fun getMovie(trackId: Int): Resource<Movie> {
        val movie = dummyMovieDataSource.firstOrNull { movie -> movie.trackId == trackId }

        return if (movie == null)
            Resource.error("Movie not found")
        else
            Resource.success(movie)
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> = flow {
        emit(dummyFaveMovieDataSource)
    }

    override suspend fun addToFavorites(movie: Movie): Resource<Unit> {
        val isAdded = dummyFaveMovieDataSource.add(movie)

        return if (isAdded)
            Resource.success(Unit)
        else
            Resource.error("Movie not added")
    }

    override suspend fun removeToFavorites(movie: Movie): Resource<Unit> {
        val isRemoved =
            dummyFaveMovieDataSource.removeIf { favoriteMovie -> favoriteMovie == movie }

        return if (isRemoved)
            Resource.success(Unit)
        else
            Resource.error("Movie not removed")

    }
}