package com.nantesmatthew.movie.data.entity

import androidx.room.*
import com.nantesmatthew.movie.domain.model.Movie

@Entity(tableName = "movie_table")
class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "track_id")
    val trackId: Int,
    @ColumnInfo(name = "track_name")
    val trackName: String,
    @ColumnInfo(name = "artist_name")
    val artistName: String,
    @ColumnInfo(name = "primary_genre")
    val primaryGenre: String,
    @ColumnInfo(name = "artwork")
    val artwork: String,
    @ColumnInfo(name = "track_price")
    val trackPrice: Double,
    @ColumnInfo(name = "duration")
    val durationInMillis: Int,
    @ColumnInfo(name = "preview_url")
    val previewUrl:String,
    @ColumnInfo(name = "short_description")
    val shortDescription:String,
    @ColumnInfo(name = "long_description")
    val longDescription: String

)
fun MovieEntity.toDomain(): Movie {
    return Movie(
        trackId = this.trackId,
        trackName = this.trackName,
        artistName = this.artistName,
        primaryGenre = this.primaryGenre,
        artwork = this.artwork,
        trackPrice = this.trackPrice,
        trackTimeMillis = this.durationInMillis,
        previewUrl = this.previewUrl,
        shortDescription = this.shortDescription,
        longDescription = this.longDescription,
        isFavorite = false
    )
}



