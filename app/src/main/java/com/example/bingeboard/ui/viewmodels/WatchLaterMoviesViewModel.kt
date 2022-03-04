package com.example.bingeboard.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bingeboard.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchLaterMoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    init {
        fetchWatchLaterMovies()
    }

    private fun fetchWatchLaterMovies() {
        viewModelScope.launch {
            moviesRepository.fetchWatchLaterMoviesList()
        }
    }

    fun addOrRemoveMoviesInWatchLater(isWatchLater:Int,movieId: Int) {
        viewModelScope.launch {
            moviesRepository.addOrRemoveMovieInWatchLater(isWatchLater,movieId)
            fetchWatchLaterMovies()
        }
    }

    fun getWatchLaterMovies() = moviesRepository.getWatchLaterMovies()
}