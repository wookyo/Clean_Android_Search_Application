package com.example.data.api

import com.example.data.model.movie.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(ApiClient.BASE_URL)
    suspend fun getSearchMovies(
        @Query(APIConstants.SEARCH_QUERY) query: String,
        @Query(APIConstants.SEARCH_PAGE) start: Int = 0
    ): MovieResponse
}