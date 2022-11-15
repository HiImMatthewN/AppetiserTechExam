package com.nantesmatthew.user_session.data.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Provides
    fun providesDatabase(@ApplicationContext context: Context): UserSessionDatabase =
        Room.databaseBuilder(context,
            UserSessionDatabase::class.java,
            "user_session_database")
            .build()

}