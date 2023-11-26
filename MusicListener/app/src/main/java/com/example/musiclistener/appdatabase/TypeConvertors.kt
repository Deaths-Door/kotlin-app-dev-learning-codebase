package com.example.musiclistener.appdatabase

import androidx.room.TypeConverter
import com.example.musiclistener.Song
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConvertorSongID {
    @TypeConverter fun listToJson(value:List<String>): String = Gson().toJson(value)
    @TypeConverter fun jsonToList(value:String):ArrayList<String> = Gson().fromJson(value,object : TypeToken<ArrayList<String>>(){}.type)
}
class ConvertorSong {
    @TypeConverter fun listToJson(value:List<Song>): String = Gson().toJson(value)
    @TypeConverter fun jsonToList(value:String): ArrayList<Song> = Gson().fromJson(value,object : TypeToken<ArrayList<Song>>(){}.type)
}