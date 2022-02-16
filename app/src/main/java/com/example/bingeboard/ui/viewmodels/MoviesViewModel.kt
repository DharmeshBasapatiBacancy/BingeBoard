package com.example.bingeboard.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bingeboard.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            moviesRepository.fetchMovies()
        }
    }

    fun getAllMovies() = moviesRepository.getAllMovies()

    /*suspend fun getMoviesFromApi() {
        var apiResponse = "Nothing"
        Log.d(TAG, "getMoviesFromApi: API Call Started")
        val apiJob = CoroutineScope(Dispatchers.IO).launch {
            val result = apiService.getAllMovies("be77fc9011234ea2a705be52eeef238b")
            Log.d(TAG, "getMoviesFromApi: GOT THE RESPONSE")
            apiResponse = if (result.isSuccessful) {
                "API CALL SUCCESS"
            } else {
                "API CALL FAILURE"
            }
        }
        apiJob.join()
        Log.d(TAG, "getMoviesFromApi: $apiResponse")
        Log.d(TAG, "getMoviesFromApi: API Call Ends")
    }*/

}