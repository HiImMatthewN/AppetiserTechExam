package com.nantesmatthew.movie.data.data_source

import com.nantesmatthew.movie.data.entity.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Data Remote Source for Movies
 */

interface MovieService {

    @GET("search")
    suspend fun search(
        @Query("term") term: String,
        @Query("country") country: String,
        @Query("media") media: String
    ): Response<MovieResponse>


}