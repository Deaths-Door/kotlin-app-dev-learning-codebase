package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.PriceRanges
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PriceRangesTypeConvertor {
    @TypeConverter
    fun fromList(value:List<PriceRanges>): String = Gson().toJson(value)
    @TypeConverter
    fun <T> fromString(value:String): List<PriceRanges> = Gson().fromJson(value,object : TypeToken<List<PriceRanges>>(){}.type)
}

