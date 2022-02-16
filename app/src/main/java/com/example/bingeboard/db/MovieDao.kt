package com.example.bingeboard.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bingeboard.network.models.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(moviesList: List<Movie>)

    @Query("SELECT * FROM movie")
    suspend fun getMovies(): List<Movie>

}