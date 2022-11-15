package com.nantesmatthew.user_session.data.module


import com.nantesmatthew.user_session.data.data_source.UserSessionDao
import com.nantesmatthew.user_session.data.repository.UserSessionRepositoryImpl
import com.nantesmatthew.user_session.domain.repository.UserSessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UserSessionModule {


    @Provides
    fun providesMovieDao(appDatabase: UserSessionDatabase): UserSessionDao =
        appDatabase.userSessionDao()

    @Provides
    fun providesMovieRepository(dao: UserSessionDao): UserSessionRepository =
        UserSessionRepositoryImpl(dao)
}