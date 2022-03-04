package com.example.bingeboard.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bingeboard.R
import com.example.bingeboard.databinding.ActivityTesterBinding
import com.example.bingeboard.ui.adapters.MovieAdapter
import com.example.bingeboard.ui.viewmodels.MoviesViewModel
import com.example.bingeboard.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TesterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTesterBinding
    private lateinit var movieAdapter: MovieAdapter
    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTesterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()

        lifecycleScope.launch {
            moviesViewModel.getMoviesList().collectLatest {
                movieAdapter.submitData(it)
            }
        }

    }
    private fun setupList() {

        movieAdapter = MovieAdapter() {

        }

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(this@TesterActivity)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

}