package com.nantesmatthew.movie.domain.use_case

import com.nantesmatthew.core.util.Resource
import com.nantesmatthew.core.util.Status
import com.nantesmatthew.movie.domain.model.Movie
import com.nantesmatthew.movie.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * A Class UseCase that handles adding or removing of favorite movies
 *
 */
class AddRemoveFromFavoritesUseCase @Inject constructor(private val repository: MovieRepository) {

    companion object {
    }

    /**
     * Remove or add movie from favorite depending on it's [Movie.isFavorite] value
     * @param movie the movie to be remove or add.
     * @return The movie selected and it's status
     */
    suspend operator fun invoke(movie: Movie): Resource<Movie> {
        if (movie.isFavorite) {
            val updateResult = repository.removeToFavorites(movie)
            if (updateResult.status == Status.ERROR)
                return Resource.error(updateResult.message ?: "Unknown Error Updating")
        } else {
            val updateResult = repository.addToFavorites(movie)
            if (updateResult.status == Status.ERROR)
                return Resource.error(updateResult.message ?: "Unknown Error Updating")
        }
        return Resource.success(movie.copy(isFavorite = !movie.isFavorite))
    }


}