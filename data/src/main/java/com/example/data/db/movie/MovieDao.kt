package com.example.data.db.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.movie.MovieEntity
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(item: MovieEntity): Long

    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE title LIKE '%' || :title || '%'")
    fun getMoviesByTitle(title: String): Flow<List<MovieEntity>>

    @Query("DELETE FROM movie")
    fun deleteAllMovies(): Completable

    @Delete
    fun deleteMovie(item: MovieEntity): Int
}