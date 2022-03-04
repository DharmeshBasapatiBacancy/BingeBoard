package com.example.bingeboard.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bingeboard.network.models.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemote(list: List<RemoteKeys>)

    @Query("SELECT * FROM remoteKeys WHERE movieId = :id")
    suspend fun getRemoteKeys(id:Int) : RemoteKeys

    @Query("DELETE FROM remoteKeys")
    suspend fun clearAll()

}