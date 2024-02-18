package com.example.search

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SearchApplication : Application() {

    // 공통 파라메터 정의
    val apiKey = "92e32667"

    companion object {
        private var instance: SearchApplication? = null

        @JvmStatic
        fun getGlobalApplicationContext(): SearchApplication?{
            return instance
        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}