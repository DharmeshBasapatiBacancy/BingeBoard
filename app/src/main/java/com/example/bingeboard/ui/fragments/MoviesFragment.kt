package com.example.bingeboard.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bingeboard.databinding.FragmentMoviesBinding
import com.example.bingeboard.network.models.Movie
import com.example.bingeboard.ui.adapters.MovieAdapter
import com.example.bingeboard.ui.viewmodels.MoviesViewModel
import com.example.bingeboard.ui.viewmodels.WatchLaterMoviesViewModel
import com.example.bingeboard.utils.Resource
import com.example.bingeboard.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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
        /*moviesViewModel.getAllMovies().observe(requireActivity()) {
            Log.d(TAG, "onCreateView: $it")
            when (it) {
                is Resource.Loading -> {
                    //Loading stuff
                }
                is Resource.Success -> {
                    it.data?.let { it1 -> updateList(it1) }
                }
                is Resource.Error -> {
                    it.message?.let { it1 -> requireContext().showToast(it1) }
                }
            }
        }*/
        lifecycleScope.launch {
            moviesViewModel.getMoviesList().collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun updateList(data: List<Movie>) {
        //movieAdapter.submitData(data)
    }

    private fun setupList() {

        movieAdapter = MovieAdapter() {
            if (it.isWatchLater == 1) {
                watchLaterMoviesViewModel.addOrRemoveMoviesInWatchLater(0,it.id)
                requireContext().showToast("Removed from Watch Later")
            } else {
                watchLaterMoviesViewModel.addOrRemoveMoviesInWatchLater(1,it.id)
                requireContext().showToast("Added to Watch Later")
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