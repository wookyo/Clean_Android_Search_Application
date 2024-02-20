package com.example.search.presention.view.search

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.search.R
import com.example.search.databinding.FragmentMovieSearchBinding
import com.example.search.presention.base.BaseBindingFragment
import com.example.search.presention.utils.ItemMoveCallback
import com.example.search.presention.utils.LogUtils
import com.example.search.presention.view.search.MovieSearchViewModel.Companion.FAVORITE
import com.example.search.presention.view.search.MovieSearchViewModel.Companion.HOME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchFragment: BaseBindingFragment<FragmentMovieSearchBinding>(), View.OnClickListener {

    private lateinit var movieAdapter: MovieAdapter

    private val viewModel: MovieSearchViewModel by viewModels()

    companion object {
        fun newInstance(): MovieSearchFragment {
            return MovieSearchFragment()
        }
    }
    override fun initData() {}

    override fun getLayoutRes(): Int {
       return R.layout.fragment_movie_search
    }

    override fun initView() {
        binding?.let {
            it.model = viewModel
            it.clickListener = this
        }
        initObserver()
        initAdapter()
    }

    private fun initAdapter() {
        movieAdapter = MovieAdapter { movie ->
            AlertDialog.Builder(context).apply {
                val message = if(movie.isFavorite) resources.getString(R.string.favorite_cancel)
                else resources.getString(R.string.favorite_add)
                setMessage(message)
                setCancelable(true)
                setPositiveButton(message, DialogInterface.OnClickListener { dialog, which ->
                    if(movie.isFavorite){
                        movie.isFavorite = false
                        viewModel.deleteLocalSearchMovie(movie)
                    }else{
                        movie.isFavorite = true
                        viewModel.insertLocalSearchMovie(movie)
                    }
                })
                show()
            }
        }
        val callback: ItemTouchHelper.Callback = ItemMoveCallback(movieAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding?.rvMovies)
        binding?.rvMovies?.adapter = movieAdapter
    }

    private fun changeBottomTabBar(currentView: Int) {
        binding?.run {
            when(currentView){
                HOME -> {
                    viewMovieHomeBottomBar.visibility = View.VISIBLE
                    viewMovieFavoriteBottomBar.visibility = View.GONE
                }
                FAVORITE ->{
                    viewMovieHomeBottomBar.visibility = View.GONE
                    viewMovieFavoriteBottomBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initObserver() {
        with(viewModel) {
            movieList.observe(viewLifecycleOwner, Observer { items ->
                LogUtils.e("TESTER", "[initObserver] : "+items)
                movieAdapter.setMovieList(items)
//                if( items.isNullOrEmpty()){
//                    showToast(requireContext(), getString(R.string.no_movie_error_msg))
//                }else {
//                    movieAdapter.setMovieList(items)
//                }
            })
        }
    }

    private fun initObserverData() {
        with(viewModel) {
            if( remoteMovieList.hasObservers()){
                remoteMovieList.removeObservers(viewLifecycleOwner)
            }
            if( localMovieList.hasObservers()){
                localMovieList.removeObservers(viewLifecycleOwner)
            }
        }
        movieAdapter.clearData()
        viewModel.resetRemoteItemOffsetInfo()
    }

    private fun setRemoteItemsObserver(){
        if (viewModel.currentView != HOME) return
        initObserverData()
        with(viewModel) {
        remoteMovieList.observe(viewLifecycleOwner, Observer { items ->
            LogUtils.e("TESTER", "[setRemoteItemsObserver] : "+items)
//                if( items.isNullOrEmpty()){
//                    showToast(requireContext(), getString(R.string.no_movie_error_msg))
//                    return@Observer
//                }
            updateMovieList(items)
            })
        requestRemoteMovie()
        }
    }

    private fun setLocalItemsObserver(){
        if (viewModel.currentView != FAVORITE) return
        initObserverData()
        with(viewModel) {
            localMovieList.observe(viewLifecycleOwner, Observer { items ->
                LogUtils.e("TESTER", "[localMovieList] : "+items)
//                if( items.isNullOrEmpty()){
//                    showToast(requireContext(), getString(R.string.no_movie_error_msg))
//                    return@Observer
//                }
                updateMovieList(items)
            })
            requestLocalMovies()
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.view_movie_home ->{
                if (viewModel.currentView == HOME) return
                viewModel.currentView = HOME
                changeBottomTabBar(HOME)
                setRemoteItemsObserver()
            }
            R.id.view_movie_favorite ->{
                if (viewModel.currentView == FAVORITE) return
                viewModel.currentView = FAVORITE
                changeBottomTabBar(FAVORITE)
                setLocalItemsObserver()
            }
        }
    }

}