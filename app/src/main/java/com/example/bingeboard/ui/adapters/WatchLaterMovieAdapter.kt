package com.example.bingeboard.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.bingeboard.BuildConfig
import com.example.bingeboard.databinding.RowItemWatchLaterMovieBinding
import com.example.bingeboard.network.models.Movie

class WatchLaterMovieAdapter(private val onItemClick: (Movie) -> Unit) :
    ListAdapter<Movie, WatchLaterMovieAdapter.ViewHolder>(DiffUtil()) {

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(private val rowItemWatchLaterMovieBinding: RowItemWatchLaterMovieBinding) :
        RecyclerView.ViewHolder(rowItemWatchLaterMovieBinding.root) {
        fun bind(item: Movie, onItemClick: (Movie) -> Unit) {
            rowItemWatchLaterMovieBinding.apply {

                tvMovieName.text = item.title
                imgMovie.load(BuildConfig.IMAGES_BASE_URL + item.poster_path)
                btnRemoveFromWatchLater.setOnClickListener {
                    onItemClick(item)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemWatchLaterMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,onItemClick)
    }

}
