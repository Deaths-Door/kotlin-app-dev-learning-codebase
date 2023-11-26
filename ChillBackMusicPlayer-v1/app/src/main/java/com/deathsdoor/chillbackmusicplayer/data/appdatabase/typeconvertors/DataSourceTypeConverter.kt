package com.deathsdoor.chillbackmusicplayer.data.appdatabase.typeconvertors

import androidx.room.TypeConverter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.DataSource
import java.io.File

class DataSourceTypeConverter {
    @TypeConverter
    fun fromDataSource(dataSource: DataSource): String = when (dataSource) {
        is DataSource.URL -> "${DataSource.TYPE.URL.name}:${dataSource.url}"
        is DataSource.FILE -> "${DataSource.TYPE.FILE.name}:${dataSource.file.absolutePath}"
    }
    @TypeConverter
    fun toDataSource(data: String): DataSource {
        val (type, value) = data.split(":")
        return when (type) {
            DataSource.TYPE.URL.name -> DataSource.URL(value)
            DataSource.TYPE.FILE.name -> DataSource.FILE(File(value))
            else -> throw IllegalArgumentException("Invalid data type: $type")
        }
    }
}
