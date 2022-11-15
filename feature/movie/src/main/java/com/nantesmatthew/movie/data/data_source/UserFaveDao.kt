package com.nantesmatthew.movie.data.data_source

import androidx.room.*
import com.nantesmatthew.movie.data.entity.FavoriteMovieEntity
import com.nantesmatthew.movie.data.entity.FavoriteMovieInfoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Local Source for User Faved Movies

 */
@Dao
interface UserFaveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteMovieInfoEntity: FavoriteMovieInfoEntity): Long

    @Delete
    suspend fun delete(favoriteMovieInfoEntity: FavoriteMovieInfoEntity): Int

    @Transaction
    @Query("SELECT * FROM favorite_movie_table")
    fun get(): Flow<List<FavoriteMovieEntity>>

    @Transaction
    @Query("SELECT * FROM favorite_movie_table WHERE track_id = :trackId")
    suspend fun get(trackId:Int): FavoriteMovieEntity?
}