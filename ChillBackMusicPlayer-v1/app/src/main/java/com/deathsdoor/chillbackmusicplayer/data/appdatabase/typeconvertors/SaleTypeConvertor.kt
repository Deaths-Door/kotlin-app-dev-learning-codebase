package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.Sales
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SaleTypeConvertor {
    @TypeConverter
    fun listToJson(value: Sales): String = Gson().toJson(value)
    @TypeConverter
    fun toClass(value:String): Sales = Gson().fromJson(value,object : TypeToken<Sales>(){}.type)
}