package com.example.search

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.search.databinding.ActivityIntroBinding
import com.example.search.presention.base.BaseBindingActivity
import com.example.search.presention.view.search.MovieSearchActivity
import kotlin.concurrent.thread

class IntroActivity: BaseBindingActivity<ActivityIntroBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_intro
    }


    override fun initData() {
    }


    override fun initView(viewDataBinding: ActivityIntroBinding) {
        if(isNetworkAvailable(this)){
            startMainActivity()
        }else {
            showToast(getString(R.string.network_error_msg))
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetwork?.let { network ->
            val actNw = connectivityManager.getNetworkCapabilities(network)
            val linkProperties = connectivityManager.getLinkProperties(network)
            return linkProperties != null
        }?: return false
    }

    private fun startMainActivity() {
        thread(start = true) {
            Thread.sleep(1000)
            startActivity(Intent(this, MovieSearchActivity::class.java))
            finish()
        }
    }
}