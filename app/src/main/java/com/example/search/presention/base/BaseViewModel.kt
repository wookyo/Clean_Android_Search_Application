package com.example.search.presention.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _percent = MutableLiveData<String>("0")
    val percent: LiveData<String> get() = _percent

    fun showProgress() {
        _isLoading.value = true
    }

    fun hideProgress() {
        _isLoading.value = false
    }

    fun progressPercent(load: String) {
        _percent.value = load
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}