package com.example.musiclistener.appdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.musiclistener.Artist

@Dao
interface ArtistDao {
    @Insert
    suspend fun insertArtist(data : Artist)
    @Query("Select * from Artist")
    fun getAllArtist(): LiveData<List<Artist>>
    @Query("DELETE FROM Artist")
    fun removeAll()
    @Query("DELETE FROM Artist where artistID == :autoId")
    fun removeArtist(autoId: Long)
    @Query("SELECT EXISTS(SELECT * FROM Artist WHERE artistID = :autoId)")
    fun artistExists(autoId: String):Boolean
}
