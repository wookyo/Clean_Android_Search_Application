package com.example.data.repository.search.local

import com.example.data.db.movie.MovieDao
import com.example.data.model.movie.MovieEntity
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) :
    MovieLocalDataSource {
    override fun insertMovies(movies: List<MovieEntity>): Completable = movieDao.insertMovies(movies)

    override fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getAllMovies()

    override fun getSearchMovies(title: String): Flow<List<MovieEntity>> = movieDao.getMoviesByTitle(title)

    override fun deleteAllMovies(): Completable = movieDao.deleteAllMovies()

    override fun deletelMovie(item: MovieEntity): Int = movieDao.deleteMovie(item)

    override fun insertMovie(item: MovieEntity): Long = movieDao.insertMovie(item)


}