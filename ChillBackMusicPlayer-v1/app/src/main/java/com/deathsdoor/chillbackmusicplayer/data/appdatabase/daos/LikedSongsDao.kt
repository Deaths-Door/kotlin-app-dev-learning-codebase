package com.deathsdoor.chillbackmusicplayer.data.appdatabase.daos

import androidx.room.*
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.MediaID

@Dao
interface LikedSongsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: MediaID)

    @Query("SELECT EXISTS (SELECT * from MediaID WHERE mediaID = :mediaID)")
    fun isLiked(mediaID:String):Boolean

    @Query("DELETE FROM MediaID WHERE mediaID = :mediaID")
    fun delete(mediaID: String)

    @Query("DELETE FROM MediaID")
    fun deleteAll()

    @Query("SELECT * from MediaID")
    fun all() : List<MediaID>?
}