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

    // 현재 검색어
    private var currentQuery: String = ""

    val query = MutableLiveData<String>()

    // 검색어 list
    private val _movieList = MutableLiveData<ArrayList<Movie>>()
    val movieList: LiveData<ArrayList<Movie>> get() = _movieList

    // 검색 결과에 따른 toast 메세지.
    private val _toastMsg = MutableLiveData<MessageSet>()
    val toastMsg: LiveData<MessageSet> get() = _toastMsg

    enum class MessageSet {
        LAST_PAGE,
        EMPTY_QUERY,
        NETWORK_NOT_CONNECTED,
        ERROR,
        SUCCESS,
        NO_RESULT,
        LOCAL_SUCCESS
    }

    fun requestMovieFlow() {
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

    // 검색한 영화 더 불러오기
    fun requestPagingMovie(offset: Int) {
        if (!checkNetworkState()) return // 네트워크 연결 유무

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

    private fun checkNetworkState(): Boolean {
        return networkManager.checkNetworkState()
    }

    private fun requestLocalMovies() {
        viewModelScope.launch {
            getLocalMoviesUseCase.getLocalSearchMovies(currentQuery)
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
}