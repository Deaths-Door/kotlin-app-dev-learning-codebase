package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.simplified.Date
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DatesTypeConvertor {
    @TypeConverter
    fun listToJson(value: Date): String = Gson().toJson(value)
    @TypeConverter
    fun jsonToList(value:String): Date = Gson().fromJson(value,object : TypeToken<Date>(){}.type)
}