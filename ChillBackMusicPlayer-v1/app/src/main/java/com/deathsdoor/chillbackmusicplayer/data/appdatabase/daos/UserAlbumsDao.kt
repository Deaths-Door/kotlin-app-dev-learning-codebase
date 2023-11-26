package com.deathsdoor.chillbackmusicplayer.data.appdatabase.daos

import androidx.room.*
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Album

//TODO add addSong function from album dataclass to interface
@Dao
interface UserAlbumsDao {
    @Insert
    fun insert(data : Album)

    @Update
    fun update(data: Album)

    @Query("SELECT * from Album")
    fun getAll() : List<Album>?

    @Query("SELECT * from Album WHERE id = :id")
    fun get(id:String) : Album

    @Query("DELETE FROM Album")
    fun deleteAll()
}