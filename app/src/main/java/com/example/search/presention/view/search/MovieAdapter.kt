package com.example.search.presention.view.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Movie
import com.example.search.R
import com.example.search.databinding.ItemMovieBinding
import com.example.search.presention.utils.ItemMoveCallback
import java.util.Collections

class MovieAdapter(private val itemClick: (Movie) -> Unit) :
    ListAdapter<Movie, MovieAdapter.ViewHolder>(diffUtil),
    ItemMoveCallback.ItemTouchInterface {

    private var movieList: ArrayList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )

        return ViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                itemClick(getItem(position))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(movieList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(movieList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(itemViewHolder: ViewHolder?) {
        itemViewHolder!!.rowView!!.setBackgroundColor(
            ContextCompat.getColor(
                itemViewHolder.context!!,
                R.color.bright_grey
            )
        )
    }

    override fun onRowClear(itemViewHolder: ViewHolder?) {
        itemViewHolder!!.rowView!!.setBackgroundColor(
            ContextCompat.getColor(
                itemViewHolder.context!!,
                R.color.white
            )
        )
    }

    fun setMovieList(movieList: ArrayList<Movie>) {
        this.movieList = movieList
    }

    class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var context: Context? = null
        var rowView: View? = null

        fun bind(movie: Movie) {
            context = binding.root.context
            rowView = binding.movieItemContainer
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    fun clearData(){
        movieList.clear()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.title == newItem.title
        }
    }
}