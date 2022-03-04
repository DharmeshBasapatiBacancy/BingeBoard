package com.example.bingeboard.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.bingeboard.db.MovieDatabase
import com.example.bingeboard.network.ApiService
import com.example.bingeboard.network.models.Movie
import com.example.bingeboard.repository.MoviesRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val apiService: ApiService,
    private val movieDatabase: MovieDatabase
) :
    ViewModel() {

    init {
        //fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            //moviesRepository.fetchMovies()
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getMoviesList(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(20, enablePlaceholders = false),
        pagingSourceFactory = { movieDatabase.movieDao().getMovies() },
        remoteMediator = MoviesRemoteMediator(apiService, movieDatabase)
    ).flow.cachedIn(viewModelScope)

    //fun getAllMovies() = moviesRepository.getAllMovies()

}