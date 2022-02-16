package com.example.bingeboard.db

import androidx.room.*
import com.example.bingeboard.network.models.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(moviesList: List<Movie>)

    @Query("SELECT * FROM movie")
    suspend fun getMovies(): List<Movie>

    @Query("SELECT * FROM movie WHERE isWatchLater=1")
    suspend fun getWatchLaterMovies(): List<Movie>

    @Query("UPDATE movie SET isWatchLater=:isWatchLater WHERE id = :movieId")
    suspend fun addOrRemoveMovieFromWatchLater(isWatchLater: Int, movieId: Int)

}