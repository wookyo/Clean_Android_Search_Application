package com.example.data.repository.search

import com.example.data.api.KtorInterface
import com.example.data.mappper.mapperToMovie
import com.example.data.model.movie.MovieEntity

import com.example.data.repository.search.local.MovieLocalDataSource
import com.example.data.repository.search.remote.MovieRemoteDataSource
import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
) : MovieRepository {

    // remote data (페이징)
    override fun getSearchMovies(query: String): Flow<List<Movie>> {
        return flow {
            movieRemoteDataSource.getSearchMovies(query).collect {
                emit(mapperToMovie(it.items))
            }
        }
    }

    // local data
    override fun getLocalSearchMovies(query: String): Flow<List<Movie>> {
        return flow{
            movieLocalDataSource.getSearchMovies(query).collect {
                emit(mapperToMovie(it))
            }
        }
    }

    // remote data (페이징)
    override fun getPagingMovies(query: String, offset: Int): Flow<List<Movie>> {
        return flow{
            movieRemoteDataSource.getSearchMovies(query,offset).collect {
                emit(mapperToMovie(it.items))
            }
        }
    }

    override fun insertLocalSearchMovie(query: Movie): Long {
        return movieLocalDataSource.insertMovie(MovieEntity(query))
    }

    override fun deleteLocalSearchMovie(query: Movie): Int {
        return movieLocalDataSource.deletelMovie(MovieEntity(query))
    }

}