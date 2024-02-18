package com.example.data.mappper

import com.example.data.model.movie.MovieEntity
import com.example.domain.model.Movie

fun mapperToMovie(movies: List<MovieEntity>): List<Movie> {
    return movies.toList().map {
        Movie(
            it.actor,
            it.director,
            it.image,
            it.link,
            it.pubDate,
            it.subtitle,
            it.title,
            it.userRating
        )
    }
}