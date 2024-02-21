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


//    private fun initAdapter() {
//        movieAdapter = MovieAdapter { movie ->
//            Intent(Intent.ACTION_VIEW, Uri.parse(movie.poster)).takeIf {
//                it.resolveActivity(packageManager) != null
//            }?.run(this::startActivity)
//        }
//
//        val callback: ItemTouchHelper.Callback = ItemMoveCallback(movieAdapter)
//        val touchHelper = ItemTouchHelper(callback)
//        touchHelper.attachToRecyclerView(binding.rvMovies)
//
//        binding.rvMovies.adapter = movieAdapter
//    }

//    private fun initObserver() {
//        viewModel.movieList.observe(this@MovieSearchActivity, Observer {
//            movieAdapter.setMovieList(it)
//        })
//    }

//    private fun initViewModelCallback() {
//        with(viewModel) {
//            toastMsg.observe(this@MovieSearchActivity, Observer {
//                when (toastMsg.value) {
//                    MovieSearchViewModel.MessageSet.LAST_PAGE -> showToast(getString(R.string.last_page_msg))
//                    MovieSearchViewModel.MessageSet.EMPTY_QUERY -> showToast(getString(R.string.search_input_query_msg))
//                    MovieSearchViewModel.MessageSet.NETWORK_NOT_CONNECTED -> showToast(getString(R.string.network_error_msg))
//                    MovieSearchViewModel.MessageSet.SUCCESS -> showToast(getString(R.string.load_movie_success_msg))
//                    MovieSearchViewModel.MessageSet.NO_RESULT -> showToast(getString(R.string.no_movie_error_msg))
//                    MovieSearchViewModel.MessageSet.ERROR -> showToast(getString(R.string.error_msg))
//                    MovieSearchViewModel.MessageSet.LOCAL_SUCCESS -> showToast(getString(R.string.local_db_msg))
//                    else -> ""
//                }
//            })
//        }
//    }
}