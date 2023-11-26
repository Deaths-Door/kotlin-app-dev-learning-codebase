package com.deathsdoor.chillbackmusicplayer.data.dataclasses

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Album(
    var songs: List<Song> = emptyList(),
    @Embedded var metadata: Metadata,
    var isPinned: Boolean = false,
){
    @Deprecated("Do not use this,just there so room doesn't give compile-time error",level = DeprecationLevel.ERROR)
    @PrimaryKey(autoGenerate = true)
    var _primaryKey: Int = 0
}