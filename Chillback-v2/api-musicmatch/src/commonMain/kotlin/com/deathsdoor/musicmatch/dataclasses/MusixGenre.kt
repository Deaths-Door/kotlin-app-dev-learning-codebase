package com.deathsdoor.musicmatch.dataclasses

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class MusixGenre(
    @SerialName("music_genre_id")
    val id: Int,
    @SerialName("music_genre_parent_id")
    val parentId: Int,
    @SerialName("music_genre_name")
    val name: String,
    @SerialName("music_genre_name_extended")
    val nameExtended: String,
    @SerialName("music_genre_vanity")
    val vanity: String?
)