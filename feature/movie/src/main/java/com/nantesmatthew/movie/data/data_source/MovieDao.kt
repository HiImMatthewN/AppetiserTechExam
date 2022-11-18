package com.nantesmatthew.movie.data.data_source

import androidx.room.*
import com.nantesmatthew.movie.data.entity.FavoriteMovieEntity
import com.nantesmatthew.movie.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Local Source for Movies
 */

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntities: List<MovieEntity>): List<Long>


    @Transaction
    @Query("SELECT * FROM movie_table")
     fun get(): Flow<List<MovieEntity>>


    @Transaction
    @Query("SELECT * FROM movie_table WHERE track_id = :trackId")
    suspend fun get(trackId: Int): MovieEntity?



}