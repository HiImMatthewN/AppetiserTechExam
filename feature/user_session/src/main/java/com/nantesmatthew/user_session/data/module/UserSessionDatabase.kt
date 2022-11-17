package com.nantesmatthew.user_session.data.module

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nantesmatthew.user_session.data.data_source.UserSessionDao
import com.nantesmatthew.user_session.data.entity.UserSessionEntity

@Database(
    entities = [UserSessionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserSessionDatabase: RoomDatabase(){
    abstract fun userSessionDao(): UserSessionDao
}