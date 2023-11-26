package com.deathsdoor.chillbackmusicplayer.data.dataclasses

import java.io.File

sealed class DataSource private constructor(){
    enum class TYPE{
        FILE,
        URL
    }
    abstract val type: TYPE
    data class URL(val url: String) : DataSource(){
        override val type: TYPE get() = TYPE.URL
    }
    data class FILE(val file: File) : DataSource(){
        constructor(filePath:String):this(File(filePath))
        override val type: TYPE get() = TYPE.FILE
    }
}