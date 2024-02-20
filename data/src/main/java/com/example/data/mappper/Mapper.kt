package com.example.data.mappper

import com.example.data.model.movie.MovieEntity
import com.example.domain.model.Movie

fun mapperToMovie(movies: List<MovieEntity>): List<Movie> {
    return movies.toList().map {
        Movie(
            it.title,
            it.year,
            it.imdbID,
            it.type,
            it.poster,
            it.isFavorite
        )
    }
}