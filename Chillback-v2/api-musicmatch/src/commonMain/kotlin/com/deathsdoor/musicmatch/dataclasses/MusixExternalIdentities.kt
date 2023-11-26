package com.deathsdoor.musicmatch.dataclasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusixExternalIdentities(
    @SerialName("spotify") val spotify:List<String>,
    @SerialName("itunes") val itunes:List<String>,
    @SerialName("amazon_music") val amazonMusic:List<String>
)