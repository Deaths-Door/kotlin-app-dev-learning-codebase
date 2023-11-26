package com.deathsdoor.chillbackmusicplayer.data.appdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Media

@Dao
sealed interface UserLikedSongs {
    @Insert
    fun insert(data : Media)

    @Query("SELECT * from Media")
    fun getAlle() : List<Media>?

    @Query("DELETE FROM Album")
    fun deleteAll()
}