package com.example.bingeboard.di

import android.content.Context
import com.example.bingeboard.db.MovieDao
import com.example.bingeboard.network.ApiService
import com.example.bingeboard.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(apiService: ApiService, movieDao: MovieDao,@ApplicationContext context: Context): MoviesRepository {
        return MoviesRepository(apiService, movieDao, context)
    }

}