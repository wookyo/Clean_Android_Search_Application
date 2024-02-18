package com.example.data.repository.search

import com.example.data.api.KtorInterface
import com.example.data.mappper.mapperToMovie

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

    override fun getSearchMoviesFlow(query: String): Flow<List<Movie>> {
        return flow {
            movieRemoteDataSource.getSearchMoviesFlow(query).collect {
                emit(mapperToMovie(it.items))
            }
        }
    }

    // 네트워크 연결이 안되는 경우 로컬에서 검색
    override fun getLocalSearchMovies(query: String): Flow<List<Movie>> {
        return flow{
            movieLocalDataSource.getSearchMovies(query).collect {
                emit(mapperToMovie(it))
            }
        }
    }




    //영화 검색 후 스크롤 내리면 영화 더 불러오기
    override fun getPagingMovies(
        query: String,
        offset: Int,
    ): Flow<List<Movie>> {
        return flow{
            movieRemoteDataSource.getSearchMoviesFlow(query,offset).collect {
                emit(mapperToMovie(it.items))
            }
        }
    }

}