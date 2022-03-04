package com.example.bingeboard.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bingeboard.databinding.FragmentWatchLaterBinding
import com.example.bingeboard.network.models.Movie
import com.example.bingeboard.ui.adapters.WatchLaterMovieAdapter
import com.example.bingeboard.ui.viewmodels.WatchLaterMoviesViewModel
import com.example.bingeboard.utils.hide
import com.example.bingeboard.utils.show
import com.example.bingeboard.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchLaterFragment : Fragment() {

    private lateinit var watchLaterMovieAdapter: WatchLaterMovieAdapter
    private lateinit var binding: FragmentWatchLaterBinding

    private val watchLaterMoviesViewModel: WatchLaterMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchLaterBinding.inflate(layoutInflater)

        watchLaterMoviesViewModel.getWatchLaterMovies().observe(requireActivity()) {
            Log.d(TAG, "onCreateView: Watch Later Movies List - ${it.data?.size}")
            if (it.data?.size!! > 0) {

                setupList(it.data)

                binding.rvWatchLaterMovies.show()
                binding.tvNotFound.hide()
                binding.imgNotFound.hide()

            } else {

                binding.rvWatchLaterMovies.hide()
                binding.imgNotFound.show()
                binding.tvNotFound.show()

            }
        }

        return binding.root
    }

    private fun setupList(data: List<Movie>) {

        watchLaterMovieAdapter = WatchLaterMovieAdapter() {
            watchLaterMoviesViewModel.addOrRemoveMoviesInWatchLater(0, it.id)
            requireContext().showToast("Removed from Watch Later")
        }

        binding.rvWatchLaterMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = watchLaterMovieAdapter
        }

        watchLaterMovieAdapter.submitList(data)

    }

    companion object {
        private const val TAG = "WatchLaterFragment"
    }

}