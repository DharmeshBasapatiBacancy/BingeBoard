package com.example.bingeboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bingeboard.databinding.FragmentMoviesBinding
import com.example.bingeboard.ui.adapters.MovieAdapter
import com.example.bingeboard.ui.viewmodels.MoviesViewModel
import com.example.bingeboard.ui.viewmodels.WatchLaterMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var moviesViewModel: MoviesViewModel
    private val watchLaterMoviesViewModel: WatchLaterMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMoviesBinding.inflate(layoutInflater)

        moviesViewModel = ViewModelProvider(requireActivity())[MoviesViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            moviesViewModel.getMoviesList().collect {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun setupList() {

        movieAdapter = MovieAdapter() {
            /*if (it.isWatchLater == 1) {
                watchLaterMoviesViewModel.addOrRemoveMoviesInWatchLater(0,it.id)
                requireContext().showToast("Removed from Watch Later")
            } else {
                watchLaterMoviesViewModel.addOrRemoveMoviesInWatchLater(1,it.id)
                requireContext().showToast("Added to Watch Later")
            }
            moviesViewModel.fetchMovies()*/
        }

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
        }
    }

    companion object {
        private const val TAG = "MoviesFragment"
    }
}