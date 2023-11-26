package com.deathsdoor.chillbackmusicplayer.data.appdatabase

import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Album
import android.content.Context
import androidx.room.*
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.dao.UserAlbumDao
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors.DataSourceTypeConverter
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors.ListSongTypeConvertor
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Media

@Database(entities = [Album::class, Media::class],version = 2, autoMigrations = [AutoMigration(1,2)])
@TypeConverters(ListSongTypeConvertor::class,DataSourceTypeConverter::class)
abstract class AppDataBase :RoomDatabase(){
    abstract val userAlbumDao : UserAlbumDao
    companion object {
        private const val _databaseName = "userdata.db"
        @Volatile private var _instance :AppDataBase? = null
        fun database(context:Context) :AppDataBase{
            return _instance ?: synchronized(this){
                _instance = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, _databaseName).build()
                _instance!!
            }
        }
    }
}