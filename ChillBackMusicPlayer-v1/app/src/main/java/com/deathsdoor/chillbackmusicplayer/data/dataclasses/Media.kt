package com.deathsdoor.chillbackmusicplayer.data.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Media(
    val dataSource: DataSource,
    @PrimaryKey(autoGenerate = false) val id:String
)