package com.nantesmatthew.user_session.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nantesmatthew.user_session.data.entity.UserSessionEntity

@Dao
interface UserSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: UserSessionEntity):Long

    @Query("SELECT * FROM user_session_table ORDER BY last_opened DESC LIMIT 1")
    suspend fun getLastSession():UserSessionEntity?



}