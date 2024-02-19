package com.example.data.repository.search.local

import com.example.data.model.movie.MovieEntity
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    fun insertMovies(movies: List<MovieEntity>): Completable
    fun getAllMovies(): Flow<List<MovieEntity>>
    fun getSearchMovies(title: String): Flow<List<MovieEntity>>
    fun deleteAllMovies(): Completable

     fun deletelMovie(item: MovieEntity): Int

     fun insertMovie(item: MovieEntity):Long
}