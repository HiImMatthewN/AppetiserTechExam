package com.nantesmatthew.movie.data.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object{
        const val ITUNES_BASE_URL = "https://itunes.apple.com/"
    }

    @Provides
    @Singleton
    fun providesOkHTTP(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(1, TimeUnit.MINUTES) // write timeout
            .readTimeout(1, TimeUnit.MINUTES) //read timeout
        return client.build()
    }

    @Provides
    @Singleton
    fun providesRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ITUNES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

}