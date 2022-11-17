package com.nantesmatthew.movie.data.module

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
    fun providesDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(context,
            MovieDatabase::class.java,
            "movie_database")
            .build()

}