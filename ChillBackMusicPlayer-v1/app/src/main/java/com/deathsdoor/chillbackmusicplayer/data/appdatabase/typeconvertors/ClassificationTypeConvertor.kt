package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.Classification
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class ClassificationTypeConvertor {
    @TypeConverter
    fun fromList(value:List<Classification>): String = Gson().toJson(value)
    @TypeConverter
    fun <T> fromString(value:String): List<Classification> = Gson().fromJson(value,object : TypeToken<List<Classification>>(){}.type)
}