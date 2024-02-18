package com.example.data.model.movie

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(

    @SerializedName("Search")
    val items: List<MovieEntity>,

    @SerializedName("totalResults")
    val totalResults: String,

    @SerializedName("Response")
    val response: String,

)