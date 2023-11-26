package com.deathsdoor.chillbackmusicplayer.data.appdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Album

@Dao
sealed interface UserAlbumDao{
    @Insert
    fun insert(data : Album)

    @Query("SELECT * from Album")
    fun getAlle() : List<Album>?

    @Query("DELETE FROM Album")
    fun deleteAll()
}