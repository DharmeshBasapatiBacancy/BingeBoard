package com.example.bingeboard.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bingeboard.BuildConfig
import com.example.bingeboard.R
import com.example.bingeboard.databinding.RowItemMoviesBinding
import com.example.bingeboard.network.models.Movie

class MovieAdapter(private val onItemClick: (Movie) -> Unit) :
    PagingDataAdapter<Movie, MovieAdapter.ViewHolder>(DiffUtil) {

    object DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(private val rowItemMoviesBinding: RowItemMoviesBinding) :
        RecyclerView.ViewHolder(rowItemMoviesBinding.root) {
        fun bind(item: Movie, onItemClick: (Movie) -> Unit) {
            rowItemMoviesBinding.apply {

                tvMovieName.text = item.title
                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGES_BASE_URL + item.poster_path)
                    .into(imgMovie)

                if (item.isWatchLater == 1) {
                    imgWatchLater.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    imgWatchLater.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }

                imgWatchLater.setOnClickListener {
                    onItemClick(item)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!, onItemClick)
    }

}
