package com.example.domain.repository

import com.example.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import io.reactivex.Flowable
import io.reactivex.Single


interface MovieRepository {
    fun getSearchMovies(
        query: String
    ): Flowable<List<Movie>>

    fun getSearchMoviesFlow(
        query: String
    ): Flow<List<Movie>>

    fun getLocalSearchMovies(
        query: String
    ): Flowable<List<Movie>>

    fun getRemoteSearchMovies(
        query: String
    ): Single<List<Movie>>

    fun getPagingMovies(
        query: String,
        offset: Int
    ): Single<List<Movie>>
}