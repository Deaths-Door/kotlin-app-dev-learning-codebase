package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SongTypeConvertor {
    @TypeConverter fun listToJson(value:List<Song>): String = Gson().toJson(value)
    @TypeConverter fun jsonToList(value:String): ArrayList<Song> = Gson().fromJson(value,object : TypeToken<ArrayList<Song>>(){}.type)
}

