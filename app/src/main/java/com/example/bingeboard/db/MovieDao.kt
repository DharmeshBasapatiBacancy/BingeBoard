package com.example.bingeboard.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.bingeboard.network.models.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(moviesList: List<Movie>)

    @Query("SELECT * FROM movie")
    fun getMovies(): PagingSource<Int, Movie>

    @Query("DELETE FROM movie")
    fun clearAll()

    @Query("SELECT * FROM movie WHERE isWatchLater=1")
    suspend fun getWatchLaterMovies(): List<Movie>

    @Query("UPDATE movie SET isWatchLater=:isWatchLater WHERE id = :movieId")
    suspend fun addOrRemoveMovieFromWatchLater(isWatchLater: Int, movieId: Int)

}