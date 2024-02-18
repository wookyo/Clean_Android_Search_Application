package com.example.data.repository.search.remote

import com.example.data.model.movie.MovieResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    fun getSearchMovies(
        query: String,
        start: Int = 1
    ): Single<MovieResponse>

    fun getSearchMoviesFlow(
        query: String,
        start: Int = 1
    ): Flow<MovieResponse>
}