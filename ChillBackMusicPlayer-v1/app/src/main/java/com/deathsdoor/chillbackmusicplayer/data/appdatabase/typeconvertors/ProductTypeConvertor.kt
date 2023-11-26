package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductTypeConvertor {
    @TypeConverter
    fun fromList(value:List<Product>): String = Gson().toJson(value)
    @TypeConverter
    fun <T> fromString(value:String): List<Product> = Gson().fromJson(value,object : TypeToken<List<Product>>(){}.type)
}

