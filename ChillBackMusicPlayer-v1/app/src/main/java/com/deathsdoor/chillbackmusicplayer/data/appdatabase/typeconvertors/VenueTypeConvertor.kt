package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.simplified.Venue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class VenueTypeConvertor {
    @TypeConverter
    fun fromList(value:List<Venue>): String = Gson().toJson(value)
    @TypeConverter
    fun <T> fromString(value:String): List<Venue> = Gson().fromJson(value,object : TypeToken<List<Venue>>(){}.type)
}