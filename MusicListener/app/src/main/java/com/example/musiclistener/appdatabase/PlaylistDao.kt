package com.example.musiclistener.appdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.musiclistener.Playlists

@Dao
interface PlaylistDao {
    @Insert
    suspend fun insertAllPlaylists(list: List<Playlists>)
    @Insert
    suspend fun insertPlaylist(data : Playlists)
    @Query("Select * from Playlists")
    fun getAllPlaylists():LiveData<List<Playlists>>
    @Update
    suspend fun updatePlaylist(playlist:Playlists)
    @Query("DELETE FROM Playlists")
    fun deleteAll()
    @Query("DELETE FROM Playlists where id == :autoId")
    fun deletePlaylist(autoId: Long)

}
