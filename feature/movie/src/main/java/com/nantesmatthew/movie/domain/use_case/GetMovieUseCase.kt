package com.nantesmatthew.movie.domain.use_case

import com.nantesmatthew.movie.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * A UseCase Class that handles all movie fetches from the [MovieRepository]
 */
class GetMovieUseCase @Inject constructor(private val repository: MovieRepository) {
    /**
     * Fetches all available movie
     * @param term term of movie
     * @param country origin country of the movie
     * @param media type of media of the movie
     */
    suspend operator fun invoke(term: String, country: String, media: String) =
        repository.getMovies(term, country, media)

    /**
     * Fetches a movie by id
     * @param trackId the trackId of the movie
     */
    suspend operator fun invoke(trackId: Int) = repository.getMovie(trackId)

    /**
     * Fetches all user fave movies
     */
    fun getFavorites() = repository.getFavoriteMovies()

}