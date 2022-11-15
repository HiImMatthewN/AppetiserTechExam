package com.nantesmatthew.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie_table")
class FavoriteMovieInfoEntity(
    @PrimaryKey
    @ColumnInfo(name = "track_id")
    val id:Int
)