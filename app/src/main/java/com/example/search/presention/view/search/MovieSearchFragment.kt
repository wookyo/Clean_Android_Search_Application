package com.example.search.presention.view.search

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.search.R
import com.example.search.databinding.FragmentMovieSearchBinding
import com.example.search.presention.base.BaseBindingFragment
import com.example.search.presention.utils.ItemMoveCallback
import com.example.search.presention.view.search.MovieSearchViewModel.ViewStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchFragment: BaseBindingFragment<FragmentMovieSearchBinding>(),
    View.OnClickListener,
    TextView.OnEditorActionListener {

    private lateinit var movieAdapter: MovieAdapter

    private val viewModel: MovieSearchViewModel by viewModels()

    private var scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisibleItemPosition =
                (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
            val itemTotalCount = recyclerView.adapter!!.itemCount-1
            if (lastVisibleItemPosition == itemTotalCount) {
                with(viewModel){
                    if (currentView != ViewStatus.FAVORITE
                        && offset * 10 <= itemTotalCount) {
                        requestPagingMovie(offset + 1)
                    }
                }
            }
        }
    }
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
            it.etInput.setOnEditorActionListener(this@MovieSearchFragment)
        }
        showKeyboardFirst()
        initObserver()
        initAdapter()
        initScrollListener()
        setRemoteItemsObserver()
    }

    private fun showKeyboardFirst() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        binding?.etInput?.requestFocus()
    }

    private fun initScrollListener() {
        when (viewModel.currentView) {
            ViewStatus.HOME -> {
                scrollListener.let {
                    binding?.rvMovies?.addOnScrollListener(it)
                }
            }
            ViewStatus.FAVORITE -> {
                scrollListener.let {
                    binding?.rvMovies?.removeOnScrollListener(it)
                }
            }
        }
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

    private fun changeBottomTabBar(currentView: ViewStatus) {
        binding?.run {
            when(currentView){
                ViewStatus.HOME -> {
                    viewMovieHomeBottomBar.visibility = View.VISIBLE
                    viewMovieFavoriteBottomBar.visibility = View.GONE
                }
                ViewStatus.FAVORITE ->{
                    viewMovieHomeBottomBar.visibility = View.GONE
                    viewMovieFavoriteBottomBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initObserver() {
        with(viewModel) {
            movieList.observe(viewLifecycleOwner, Observer { items ->
                movieAdapter.setMovieList(items)
            })
        }
    }

    private fun initObserverData() {
        with(viewModel) {
            if (remoteMovieList.hasObservers()) {
                remoteMovieList.removeObservers(viewLifecycleOwner)
            }
            if (localMovieList.hasObservers()) {
                localMovieList.removeObservers(viewLifecycleOwner)
            }
        }
        movieAdapter.clearData()
        movieAdapter.notifyDataSetChanged()
        viewModel.resetVisibleItemInfo()
    }

    private fun setRemoteItemsObserver() {
        initObserverData()
        initScrollListener()
        with(viewModel) {
            remoteMovieList.observe(viewLifecycleOwner, Observer { items ->
                if (viewModel.currentView == ViewStatus.FAVORITE) return@Observer
                viewModel.updateMovieList(items)
            })
            viewModel.requestRemoteMovie()
        }
    }

    private fun setLocalItemsObserver() {
        initObserverData()
        initScrollListener()
        with(viewModel) {
            localMovieList.observe(viewLifecycleOwner, Observer { items ->
                if (currentView == ViewStatus.HOME) return@Observer
                updateMovieList(items)
            })
            requestLocalMovies()
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_search -> {
                with(viewModel) {
                    currentQuery?.let { currentText ->
                        if (currentText == previousQuery) {
                            return@with
                        }
                        movieAdapter.clearData()
                        movieAdapter.notifyDataSetChanged()
                        viewModel.resetVisibleItemInfo()
                        requestRemoteMovie()
                    }
                }
            }
            R.id.view_movie_home ->{
                if (viewModel.currentView == ViewStatus.HOME) return
                viewModel.currentView = ViewStatus.HOME
                changeBottomTabBar(ViewStatus.HOME)
                setRemoteItemsObserver()
            }
            R.id.view_movie_favorite ->{
                if (viewModel.currentView == ViewStatus.FAVORITE) return
                viewModel.currentView = ViewStatus.FAVORITE
                changeBottomTabBar(ViewStatus.FAVORITE)
                setLocalItemsObserver()
            }
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        when (actionId){
            EditorInfo.IME_ACTION_SEARCH ->{
                with(viewModel) {
                    currentQuery?.let { currentText ->
                        if (currentText == previousQuery) {
                            return@with
                        }
                        movieAdapter.clearData()
                        movieAdapter.notifyDataSetChanged()
                        viewModel.resetVisibleItemInfo()
                        requestRemoteMovie()
                    }
                }
            }
        }
        return true
    }

}