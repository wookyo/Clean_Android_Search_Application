package com.example.data.repository.search.remote

import com.example.data.model.movie.MovieResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {

    fun getSearchMoviesFlow(
        query: String,
        start: Int = 1
    ): Flow<MovieResponse>
}