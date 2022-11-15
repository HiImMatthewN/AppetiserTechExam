package com.nantesmatthew.movie.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.nantesmatthew.movie.domain.model.Movie

class FavoriteMovieEntity(


    @Embedded
    val favoriteMovieInfoEntity: FavoriteMovieInfoEntity,


    @Relation(parentColumn = "track_id", entityColumn = "track_id")
    val movieEntity: MovieEntity


    )

fun FavoriteMovieEntity.toDomain(): Movie {
    return Movie(
        trackId = this.movieEntity.trackId,
        trackName = this.movieEntity.trackName,
        artistName = this.movieEntity.artistName,
        primaryGenre = this.movieEntity.primaryGenre,
        artwork = this.movieEntity.artwork,
        trackPrice = this.movieEntity.trackPrice,
        trackTimeMillis = this.movieEntity.durationInMillis,
        shortDescription =  this.movieEntity.shortDescription,
        longDescription = this.movieEntity.longDescription,
        isFavorite = true
    )
}