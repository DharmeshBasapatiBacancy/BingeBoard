package com.example.bingeboard.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bingeboard.databinding.FragmentMoviesBinding
import com.example.bingeboard.network.models.Movie
import com.example.bingeboard.ui.adapters.MovieAdapter
import com.example.bingeboard.ui.viewmodels.MoviesViewModel
import com.example.bingeboard.ui.viewmodels.WatchLaterMoviesViewModel
import com.example.bingeboard.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: FragmentMoviesBinding

    private val moviesViewModel: MoviesViewModel by viewModels()
    private val watchLaterMoviesViewModel: WatchLaterMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMoviesBinding.inflate(layoutInflater)

        setupList()
        setupObserver()

        return binding.root
    }

    private fun setupObserver() {
        moviesViewModel.getAllMovies().observe(requireActivity()) {
            Log.d(TAG, "onCreateView: $it")
            when (it) {
                is Resource.Loading -> {
                    //Loading stuff
                }
                is Resource.Success -> {
                    it.data?.let { it1 -> updateList(it1) }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun updateList(data: List<Movie>) {
        movieAdapter.submitList(data)
    }

    private fun setupList() {

        movieAdapter = MovieAdapter() {
            if (it.isWatchLater == 1) {
                watchLaterMoviesViewModel.removeFromWatchLater(it.id)
                Toast.makeText(requireContext(), "Removed from Watch Later", Toast.LENGTH_SHORT)
                    .show()
            } else {
                watchLaterMoviesViewModel.addToWatchLater(it.id)
                Toast.makeText(requireContext(), "Added to Watch Later", Toast.LENGTH_SHORT).show()
            }
            moviesViewModel.fetchMovies()
        }

        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
        }
    }

    companion object {
        private const val TAG = "MoviesFragment"
    }
}