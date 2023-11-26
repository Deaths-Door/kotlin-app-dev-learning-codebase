package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class StringTypeConvertor {
    @TypeConverter fun listToJson(value:List<String>): String = Gson().toJson(value)
    @TypeConverter fun jsonToList(value:String): ArrayList<String> = Gson().fromJson(value,object : TypeToken<ArrayList<String>>(){}.type)
}