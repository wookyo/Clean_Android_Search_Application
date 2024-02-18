package com.example.domain.usecase.movie

import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(
        query: String,
    ): Flowable<List<Movie>> = repository.getSearchMovies(query)

    fun getFlowData(
        query: String,
    ): Flow<List<Movie>> = repository.getSearchMoviesFlow(query)
}