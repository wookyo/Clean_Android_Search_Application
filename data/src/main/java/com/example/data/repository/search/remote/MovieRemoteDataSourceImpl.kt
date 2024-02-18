package com.example.data.repository.search.remote

import com.example.data.api.ApiInterface
import com.example.data.model.movie.MovieResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(private val apiInterface: ApiInterface) :
    MovieRemoteDataSource {

    override fun getSearchMovies(query: String, start: Int): Flow<MovieResponse> {
        return flow {
            emit(apiInterface.getSearchMovies(query, start))
        }
    }
}