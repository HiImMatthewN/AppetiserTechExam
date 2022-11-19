package com.nantesmatthew.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 *
 * Data Object Class of a Genre and its Movies
 *
 */

@Parcelize
data class MoviesByGenre(
    val title:String,
    val movies:List<Movie>
):Parcelable