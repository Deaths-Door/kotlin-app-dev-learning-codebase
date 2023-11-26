package com.deathsdoor.chillback.common.data.music

import com.deathsdoor.astroplayer.core.dataclasses.MediaItem
import com.deathsdoor.uuid.UUID
import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id : UUID = UUID.random(),
    val name : String,
    val image : String? = null,
    val songs : MutableList<MediaItem> = mutableListOf(),
    val isPinned : Boolean = false
)