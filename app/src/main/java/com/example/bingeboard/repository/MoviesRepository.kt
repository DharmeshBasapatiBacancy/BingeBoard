package com.example.bingeboard.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bingeboard.BuildConfig
import com.example.bingeboard.network.ApiService
import com.example.bingeboard.network.models.Movie
import com.example.bingeboard.utils.Resource
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val apiService: ApiService) {

    private val moviesResponse = MutableLiveData<Resource<List<Movie>>>()

    suspend fun fetchMovies() {

        moviesResponse.postValue(Resource.Loading(null))

        val apiResult = apiService.getAllMovies(BuildConfig.API_KEY)

        if (apiResult.isSuccessful && apiResult.body() != null) {
            moviesResponse.postValue(Resource.Success(apiResult.body()!!.results))
        } else {
            moviesResponse.postValue(Resource.Error(null, apiResult.errorBody().toString()))
        }

    }

    fun getAllMovies(): LiveData<Resource<List<Movie>>> = moviesResponse

}