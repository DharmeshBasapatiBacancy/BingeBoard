package com.example.bingeboard.di

import com.example.bingeboard.network.ApiService
import com.example.bingeboard.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(apiService: ApiService): MoviesRepository {
        return MoviesRepository(apiService)
    }

}