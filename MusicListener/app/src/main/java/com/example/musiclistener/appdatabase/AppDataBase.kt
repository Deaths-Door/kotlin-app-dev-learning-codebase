package com.example.musiclistener.appdatabase

import android.content.Context
import androidx.room.*
import com.example.musiclistener.Artist
import com.example.musiclistener.Constants.MUSIC_DATA_DB
import com.example.musiclistener.LocalSong
import com.example.musiclistener.Playlists

@Database(entities = [Playlists::class,Artist::class,LocalSong::class], version = 5)
@TypeConverters(ConvertorSong::class,ConvertorSongID::class)
abstract class AppDataBase: RoomDatabase(){
    abstract fun playlistDao(): PlaylistDao
    abstract fun artistDao() : ArtistDao
    abstract fun localMusicDao(): LocalMusicDao
    companion object{
        //singleton prevents multiple instance of database opened at the same time
        @Volatile
        private var INSTANCE : AppDataBase? = null
        fun getDataBase(context: Context): AppDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, MUSIC_DATA_DB).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
