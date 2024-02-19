package com.example.domain.repository

import com.example.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import io.reactivex.Flowable
import io.reactivex.Single


interface MovieRepository {

    // remote data (페이징)
    fun getSearchMovies(
        query: String
    ): Flow<List<Movie>>

    // local data
    fun getLocalAllMovies(
    ): Flow<List<Movie>>

    // remote data (페이징)
    fun getPagingMovies(
        query: String,
        offset: Int
    ): Flow<List<Movie>>

    fun insertLocalSearchMovie(
        query: Movie
    ): Long

    fun deleteLocalSearchMovie(
        query: Movie
    ): Int
}