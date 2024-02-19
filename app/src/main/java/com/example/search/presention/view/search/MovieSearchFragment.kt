package com.example.search.presention.view.search

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.search.R
import com.example.search.databinding.FragmentMovieSearchBinding
import com.example.search.presention.base.BaseBindingFragment
import com.example.search.presention.utils.ItemMoveCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchFragment: BaseBindingFragment<FragmentMovieSearchBinding>() {

    private lateinit var movieAdapter: MovieAdapter

    private val viewModel: MovieSearchViewModel by viewModels()

    companion object {
        fun newInstance(): MovieSearchFragment {
            val fragment = MovieSearchFragment()
            return fragment
        }
    }
    override fun initData() {}

    override fun getLayoutRes(): Int {
       return R.layout.fragment_movie_search
    }

    override fun initView() {
        binding?.model = viewModel
        initViewModelCallback()
        initObserver()
        initAdapter()
    }

    private fun initAdapter() {
        movieAdapter = MovieAdapter { movie ->
//            Intent(Intent.ACTION_VIEW, Uri.parse(movie.poster)).takeIf {
//                it.resolveActivity(packageManager) != null
//            }?.run(this::startActivity)
        }

        val callback: ItemTouchHelper.Callback = ItemMoveCallback(movieAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding?.rvMovies)

        binding?.rvMovies?.adapter = movieAdapter
    }

    private fun initObserver() {
        viewModel.movieList.observe(viewLifecycleOwner, Observer { items ->
            if( items.isNullOrEmpty()){
                showToast(requireContext(), getString(R.string.no_movie_error_msg))
            }else {
                movieAdapter.setMovieList(items)
            }
        })
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            toastMsg.observe(viewLifecycleOwner, Observer {
                when (toastMsg.value) {
                    MovieSearchViewModel.MessageSet.LAST_PAGE -> showToast(requireContext(), getString(R.string.last_page_msg))
                    MovieSearchViewModel.MessageSet.EMPTY_QUERY -> showToast(requireContext(),getString(R.string.search_input_query_msg))
                    MovieSearchViewModel.MessageSet.NETWORK_NOT_CONNECTED -> showToast(requireContext(),getString(R.string.network_error_msg))
                    MovieSearchViewModel.MessageSet.SUCCESS -> showToast(requireContext(),getString(R.string.load_movie_success_msg))
                    MovieSearchViewModel.MessageSet.NO_RESULT -> showToast(requireContext(),getString(R.string.no_movie_error_msg))
                    MovieSearchViewModel.MessageSet.ERROR -> showToast(requireContext(),getString(R.string.error_msg))
                    MovieSearchViewModel.MessageSet.LOCAL_SUCCESS -> showToast(requireContext(),getString(R.string.local_db_msg))
                    else -> ""
                }
            })
        }
    }

}