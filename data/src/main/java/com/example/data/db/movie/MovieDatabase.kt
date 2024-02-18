package com.example.data.db.movie

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.movie.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}