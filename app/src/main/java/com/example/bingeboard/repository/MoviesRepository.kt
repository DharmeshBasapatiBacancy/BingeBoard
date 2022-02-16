package com.example.bingeboard.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bingeboard.BuildConfig
import com.example.bingeboard.db.MovieDao
import com.example.bingeboard.network.ApiService
import com.example.bingeboard.network.models.Movie
import com.example.bingeboard.utils.NetworkUtils
import com.example.bingeboard.utils.Resource
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    private val context: Context
) {

    private val moviesResponse = MutableLiveData<Resource<List<Movie>>>()

    suspend fun fetchMovies() {

        moviesResponse.postValue(Resource.Loading(null))

        if(NetworkUtils.isNetworkConnected(context)){
            Log.d(TAG, "fetchMovies: NETWORK FOUND")
            val apiResult = apiService.getAllMovies(BuildConfig.API_KEY)

            if (apiResult.isSuccessful && apiResult.body() != null) {
                movieDao.addMovies(apiResult.body()!!.results)
                moviesResponse.postValue(Resource.Success(movieDao.getMovies()))
            } else {
                moviesResponse.postValue(Resource.Error(null, apiResult.errorBody().toString()))
            }
        }else{
            Log.d(TAG, "fetchMovies: NETWORK NOT FOUND")
            moviesResponse.postValue(Resource.Success(movieDao.getMovies()))
        }

    }

    fun getAllMovies(): LiveData<Resource<List<Movie>>> = moviesResponse

    companion object {
        private const val TAG = "MoviesRepository"
    }

}