package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.simplified.Attraction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AttractionTypeConvertor {
    @TypeConverter
    fun fromList(value:List<Attraction>): String = Gson().toJson(value)
    @TypeConverter
    fun <T> fromString(value:String): List<Attraction> = Gson().fromJson(value,object : TypeToken<List<Attraction>>(){}.type)
}