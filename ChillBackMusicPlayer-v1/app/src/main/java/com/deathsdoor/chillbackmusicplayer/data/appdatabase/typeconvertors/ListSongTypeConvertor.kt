package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Song
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListSongTypeConvertor {
    @TypeConverter fun listToJson(value:List<Song>): String = Gson().toJson(value)
    @TypeConverter fun jsonToList(value:String): List<Song> = Gson().fromJson(value,object : TypeToken<List<Song>>(){}.type)
}

