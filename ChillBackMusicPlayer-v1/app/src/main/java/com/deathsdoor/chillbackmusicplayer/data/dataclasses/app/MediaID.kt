package com.deathsdoor.chillbackmusicplayer.data.dataclasses.app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MediaID(
    @PrimaryKey(autoGenerate = false) val mediaID:String
)