package com.nantesmatthew.movie.domain.use_case

import com.nantesmatthew.movie.domain.model.Movie
import javax.inject.Inject

/**
 * A Class UseCase that filters movie list
 *
 */
class FilterMovieUseCase @Inject constructor() {

    /**
     * Filters movie by search query
     * @param movies the list to be filtered
     * @param filter the query to be find
     * @return if filter is empty or blank, returns the whole list else returns the filtered list
     */
    operator fun invoke(movies:List<Movie>,filter:String):List<Movie> =
    if (filter.isBlank()) movies else movies.filter { it.trackName.lowercase().contains(filter.trim().lowercase()) }

}