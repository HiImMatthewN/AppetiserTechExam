package com.nantesmatthew.movie.data.entity

import com.google.gson.annotations.SerializedName
import com.nantesmatthew.movie.domain.model.Movie


class MovieNetwork(
    @SerializedName("trackId")
    val trackId: Int,
    @SerializedName("trackName")
    val trackName: String?,
    @SerializedName("artistName")
    val artistName: String?,
    @SerializedName("primaryGenreName")
    val primaryGenre: String?,
    @SerializedName("artworkUrl100")
    val artwork: String?,
    @SerializedName("trackPrice")
    val trackPrice: Double?,
    @SerializedName("trackTimeMillis")
    val trackTimeMillis: Int?,
    @SerializedName("shortDescription")
    val shortDescription: String?,
    @SerializedName("longDescription")
    val longDescription: String?
)

fun MovieNetwork.toDomain(): Movie {
    return Movie(
        trackId = this.trackId,
        trackName = this.trackName ?: "",
        artistName = this.artistName ?: "",
        primaryGenre = this.primaryGenre ?: "",
        artwork = this.artwork ?: "",
        trackPrice = this.trackPrice ?: 0.0,
        trackTimeMillis = this.trackTimeMillis ?: 0,
        shortDescription = this.shortDescription ?: "",
        longDescription = this.longDescription ?: ""
    )
}

fun MovieNetwork.toLocalEntity(): MovieEntity {
    return MovieEntity(
        trackId = this.trackId,
        trackName = if (this.trackName.isNullOrBlank()) "N/A" else this.trackName,
        artistName = if (this.artistName.isNullOrBlank()) "N/A" else this.artistName,
        primaryGenre = if (this.primaryGenre.isNullOrBlank()) "N/A" else this.primaryGenre,
        artwork = if (this.artwork.isNullOrBlank()) "N/A" else this.artwork,
        trackPrice = this.trackPrice ?: 0.0,
        durationInMillis = this.trackTimeMillis ?: 0,
        shortDescription = if (this.shortDescription.isNullOrBlank()) "N/A" else this.shortDescription,
        longDescription = if (this.longDescription.isNullOrBlank()) "N/A" else this.longDescription
    )


}