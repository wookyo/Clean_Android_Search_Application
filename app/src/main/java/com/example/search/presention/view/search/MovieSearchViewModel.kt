package com.example.search.presention.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Movie
import com.example.domain.usecase.movie.GetLocalMoviesUseCase
import com.example.domain.usecase.movie.GetMoviesUseCase

import com.example.search.presention.base.BaseViewModel
import com.example.search.presention.utils.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getLocalMoviesUseCase: GetLocalMoviesUseCase,
    private val networkManager: NetworkManager,
) : BaseViewModel() {

    companion object {
        const val HOME = 100
        const val FAVORITE = 101
    }
    // 현재 검색어
    private var currentQuery: String = ""

    val query = MutableLiveData<String>()
    var offset:Int = 0

    // 검색어 visible list
    private val _movieList = MutableLiveData<ArrayList<Movie>>()
    val movieList: LiveData<ArrayList<Movie>> get() = _movieList

    // remote data list
    private val _remoteMovieList = MutableLiveData<ArrayList<Movie>>()
    val remoteMovieList: LiveData<ArrayList<Movie>> get() = _remoteMovieList

    // local data list
    private val _localMovieList = MutableLiveData<ArrayList<Movie>>()
    val localMovieList: LiveData<ArrayList<Movie>> get() = _localMovieList


    // 검색 결과에 따른 toast 메세지.
    private val _toastMsg = MutableLiveData<MessageSet>()
    val toastMsg: LiveData<MessageSet> get() = _toastMsg

    // view 상태
    var currentView = HOME

    val job = SupervisorJob()

    enum class MessageSet {
        EMPTY_QUERY,
        ERROR,
        SUCCESS,
        NO_RESULT
    }

    fun requestRemoteMovie() {
        currentQuery = query.value.toString().trim()
        if (currentQuery.isEmpty()) {
            _toastMsg.value = MessageSet.EMPTY_QUERY
            return
        }
        if (!checkNetworkState()) return

        viewModelScope.launch {
            getMoviesUseCase.getFlowData(currentQuery)
                .onStart { showProgress() }
                .onCompletion { hideProgress() }
                .catch {
                    _toastMsg.value = MessageSet.ERROR
                }
                .collect { movies ->
                    if (movies.isEmpty()) {
                        _toastMsg.value = MessageSet.NO_RESULT
                    } else {
                        _movieList.value = movies as ArrayList<Movie>
                        _toastMsg.value = MessageSet.SUCCESS
                    }
                }
        }
    }

    fun requestPagingMovie(offset: Int) {
        if (!checkNetworkState()) return

        viewModelScope.launch {
            getMoviesUseCase.getFlowData(currentQuery, offset)
                .onStart { showProgress() }
                .onCompletion { hideProgress() }
                .catch {
                    _toastMsg.value = MessageSet.ERROR
                }
                .collect { movies ->
                    if (movies.isEmpty()) {
                        _toastMsg.value = MessageSet.NO_RESULT
                    } else {
                        val pagingMovieList = _movieList.value
                        pagingMovieList?.addAll(movies)
                        _movieList.value = pagingMovieList!!
                        _toastMsg.value = MessageSet.SUCCESS
                    }
                }
        }
    }

     fun requestLocalMovies() {
        viewModelScope.launch {
            getLocalMoviesUseCase.getLocalAllMovies()
                .onStart { showProgress() }
                .onCompletion { hideProgress() }
                .catch {
                    _toastMsg.value = MessageSet.ERROR
                }
                .collect { movies ->
                    if (movies.isEmpty()) {
                        _toastMsg.value = MessageSet.NO_RESULT
                    } else {
                        _movieList.value = movies as ArrayList<Movie>
                        _toastMsg.value = MessageSet.SUCCESS
                    }
                }
        }
    }

    fun insertLocalSearchMovie(item: Movie) {
        CoroutineScope(Dispatchers.IO + job).launch {
            getLocalMoviesUseCase.insertLocalSearchMovie(item)
        }
    }

    fun deleteLocalSearchMovie(item: Movie) {
        CoroutineScope(Dispatchers.IO + job).launch {
            getLocalMoviesUseCase.deleteLocalSearchMovie(item)
        }
    }

    private fun checkNetworkState(): Boolean {
        return networkManager.checkNetworkState()
    }
    fun resetRemoteItemOffsetInfo() {
        this.offset = 0
    }

    fun updateMovieList(list: ArrayList<Movie>){
        _movieList.value = list
    }

}