package com.example.data.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Movie
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "movie")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("Title")
    var title: String = "",

    @SerializedName("Year")
    var year: String = "",

    @SerializedName("imdbID")
    var imdbID: String = "",

    @SerializedName("Type")
    var type: String = "",

    @SerializedName("Poster")
    var poster: String = ""
){

    constructor(movie: Movie) : this() {
        convertMovieToEntity(movie)
    }

    fun convertMovieToEntity(movie: Movie) {
        movie.title.let { this.title = it }
        movie.year.let { this.year = it }
        movie.imdbID.let { this.imdbID = it }
        movie.type.let { this.type = it }
        movie.poster.let { this.poster = it }
    }
}

