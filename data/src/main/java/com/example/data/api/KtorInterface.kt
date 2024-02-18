package com.example.data.api

import com.example.data.model.movie.MovieResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface KtorInterface {

    suspend fun requestMoveSearchData(
         query: String,
         start: Int = 0
    ): Flow<MovieResponse>
}