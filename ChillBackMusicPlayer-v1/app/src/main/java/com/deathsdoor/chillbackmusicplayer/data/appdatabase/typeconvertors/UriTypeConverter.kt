package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import android.net.Uri
import androidx.room.TypeConverter

class UriTypeConverter {
    @TypeConverter
    fun fromUri(uri: Uri?): String? = uri?.toString()
    @TypeConverter
    fun toUri(uriString: String?): Uri? = uriString?.let { Uri.parse(it) }
}