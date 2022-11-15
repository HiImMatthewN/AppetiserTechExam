package com.nantesmatthew.movie.data.module

import com.nantesmatthew.movie.data.data_source.MovieDao
import com.nantesmatthew.movie.data.data_source.MovieService
import com.nantesmatthew.movie.data.data_source.UserFaveDao
import com.nantesmatthew.movie.data.repository.MovieRepositoryImpl
import com.nantesmatthew.movie.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Provides
    fun providesMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun providesMovieDao(appDatabase: MovieDatabase): MovieDao =
        appDatabase.movieDao()

    @Provides
    @Singleton
    fun providesUserFaveDao(appDatabase: MovieDatabase): UserFaveDao = appDatabase.userFaveDao()

    @Provides
    @Singleton
    fun providesMovieRepository(
        api: MovieService,
        dao: MovieDao,
        userFaveDao: UserFaveDao
    ): MovieRepository =
        MovieRepositoryImpl(api, dao, userFaveDao)
}