package com.example.domain.repository

import com.example.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import io.reactivex.Flowable
import io.reactivex.Single


interface MovieRepository {

    fun getSearchMoviesFlow(
        query: String
    ): Flow<List<Movie>>

    fun getLocalSearchMovies(
        query: String
    ): Flow<List<Movie>>

    fun getPagingMovies(
        query: String,
        offset: Int
    ): Flow<List<Movie>>
}