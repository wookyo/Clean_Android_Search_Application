package com.example.search.presention.view.search

import com.example.search.R
import com.example.search.databinding.ActivityMovieSearchBinding
import com.example.search.presention.base.BaseBindingActivity
import com.example.search.presention.utils.ActivityUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchActivity: BaseBindingActivity<ActivityMovieSearchBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_movie_search
    }

    override fun initData() {

    }

    override fun initView(viewDataBinding: ActivityMovieSearchBinding) {
        setFragment()
    }

    private fun setFragment() {
        val searchFragment: MovieSearchFragment = MovieSearchFragment.newInstance()
        ActivityUtils.replaceFragment(supportFragmentManager, searchFragment, R.id.container, "")
    }
}