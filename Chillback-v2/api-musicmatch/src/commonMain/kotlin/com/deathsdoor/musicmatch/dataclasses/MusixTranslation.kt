package com.deathsdoor.musicmatch.dataclasses

import kotlinx.serialization.*

@Serializable
data class MusixTranslation(
    @SerialName("language") val sprache: String,
    @SerialName("translation") val translation: String
)