package com.example.bingeboard.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bingeboard.db.MovieDatabase
import com.example.bingeboard.network.ApiService
import com.example.bingeboard.ui.viewmodels.MoviesViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val apiService: ApiService,
    private val movieDatabase: MovieDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(apiService, movieDatabase) as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }
}