package com.nantesmatthew.movie.data.module

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nantesmatthew.movie.data.data_source.MovieDao
import com.nantesmatthew.movie.data.data_source.UserFaveDao
import com.nantesmatthew.movie.data.entity.FavoriteMovieInfoEntity
import com.nantesmatthew.movie.data.entity.MovieEntity

@Database(
    entities = [MovieEntity::class,FavoriteMovieInfoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase(){
    abstract fun movieDao(): MovieDao
    abstract fun userFaveDao():UserFaveDao
}