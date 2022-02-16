package com.example.bingeboard.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bingeboard.databinding.FragmentWatchLaterBinding
import com.example.bingeboard.network.models.Movie
import com.example.bingeboard.ui.adapters.WatchLaterMovieAdapter
import com.example.bingeboard.ui.viewmodels.WatchLaterMoviesViewModel
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

                binding.rvWatchLaterMovies.visibility = View.VISIBLE
                binding.tvNotFound.visibility = View.GONE
                binding.imgNotFound.visibility = View.GONE

            } else {

                binding.rvWatchLaterMovies.visibility = View.GONE
                binding.imgNotFound.visibility = View.VISIBLE
                binding.tvNotFound.visibility = View.VISIBLE

            }
        }

        return binding.root
    }

    private fun setupList(data: List<Movie>) {

        watchLaterMovieAdapter = WatchLaterMovieAdapter() {
            watchLaterMoviesViewModel.removeFromWatchLater(it.id)
            Toast.makeText(requireContext(), "Removed from Watch Later", Toast.LENGTH_SHORT).show()
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