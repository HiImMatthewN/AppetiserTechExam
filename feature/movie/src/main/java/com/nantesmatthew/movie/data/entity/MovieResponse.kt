package com.nantesmatthew.movie.data.entity

import com.google.gson.annotations.SerializedName

class MovieResponse(
    @SerializedName("resultCount")
    val resultCount:Int,
    @SerializedName("results")
    val results:List<MovieNetwork>?

)