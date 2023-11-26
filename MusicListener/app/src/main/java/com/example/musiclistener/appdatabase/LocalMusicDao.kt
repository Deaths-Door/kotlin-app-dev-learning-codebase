package com.example.musiclistener.appdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.musiclistener.LocalSong

@Dao
interface LocalMusicDao {
    @Insert
    fun insertAllMusic(data : List<LocalSong>)
    @Insert
    fun insertMusic(data : LocalSong)
    @Update
    suspend fun updateMusic(data: LocalSong)
    @Query("Select * from LocalSong")
    fun getAllMusic(): LiveData<List<LocalSong>>
    @Query("Select * from LocalSong WHERE mediaID = :id")
    fun getMusic(id:String) : LocalSong?
}