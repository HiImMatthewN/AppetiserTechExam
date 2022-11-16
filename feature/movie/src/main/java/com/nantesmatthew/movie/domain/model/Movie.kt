package com.nantesmatthew.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.concurrent.TimeUnit

/**
 *
 * Data Object Class of a Movie
 *
 */
@Parcelize
data class Movie(
    val trackId: Int,
    val trackName: String,
    val artistName: String,
    val primaryGenre:String,
    val artwork:String,
    val trackPrice:Double,
    val trackTimeMillis:Int,
    val previewUrl:String,
    val shortDescription:String,
    val longDescription:String,
    val isFavorite:Boolean = false
): Parcelable{

    fun getRuntime():String{
        return converter(trackTimeMillis.toLong())
    }
    //Coverts the movie millis into a 2h:30min format
    private fun converter(millis: Long): String =
        String.format(
            "%2dh %02dmin",
            TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(millis)
            )

        )


}