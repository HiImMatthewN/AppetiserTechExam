package com.nantesmatthew.movie.domain.use_case

import com.nantesmatthew.movie.domain.model.Movie
import com.nantesmatthew.movie.domain.model.MoviesByGenre
import javax.inject.Inject

/**
 * A UseCase Class that sorts movies by genre
 *
 */
class SortByGenreUseCase @Inject constructor(){
    /**
     *
     * Sorts and Categorizes by Genre then sorts Genre and it's movies  alphabetically
     *  @param movies -  raw list of movies
     *  @return the sorted list
     */
    operator fun invoke(movies: List<Movie>): List<MoviesByGenre> {
        val groupByGenre = movies.groupBy { movie -> movie.primaryGenre }

        return groupByGenre.map { (genre, movies) ->
            MoviesByGenre(genre, movies.sortedBy { it.trackName })
        }.toList().sortedBy { it.genre }

    }
}