package com.example.domain.usecase.movie

import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

     fun getLocalAllMovies(): Flow<List<Movie>> = repository.getLocalAllMovies()

    fun insertLocalSearchMovie(item: Movie) = repository.insertLocalSearchMovie(item)

    fun deleteLocalSearchMovie(item: Movie) = repository.deleteLocalSearchMovie(item)
}