package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.Promoter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PromoterTypeConvertor {
    @TypeConverter
    fun listToJson(value: Promoter): String = Gson().toJson(value)
    @TypeConverter
    fun jsonToList(value:String): Promoter = Gson().fromJson(value,object : TypeToken<Promoter>(){}.type)
}