package com.example.data.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "movie")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: String,

    @SerializedName("imdbID")
    val imdbID: String,

    @SerializedName("Type")
    val type: String,

    @SerializedName("Poster")
    val poster: String

)