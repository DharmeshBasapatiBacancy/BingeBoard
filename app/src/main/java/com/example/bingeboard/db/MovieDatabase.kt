package com.example.bingeboard.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bingeboard.network.models.Movie
import com.example.bingeboard.network.models.RemoteKeys

@Database(entities = [Movie::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}