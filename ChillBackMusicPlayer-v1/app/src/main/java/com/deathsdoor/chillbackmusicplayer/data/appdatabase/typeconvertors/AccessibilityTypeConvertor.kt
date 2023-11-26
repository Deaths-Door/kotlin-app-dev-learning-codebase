package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.Accessibility
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AccessibilityTypeConvertor {
    @TypeConverter
    fun listToJson(value: Accessibility): String = Gson().toJson(value)
    @TypeConverter
    fun jsonToList(value:String): Accessibility = Gson().fromJson(value,object : TypeToken<Accessibility>(){}.type)
}

