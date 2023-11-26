package com.deathsdoor.musicmatch.dataclasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusixLyrics(
    @SerialName("lyrics_id") val id:Int,
    @SerialName("restricted")  val isRestricted:Boolean = false,
    @SerialName("instrumental")  val isInstrumental:Boolean = false,
    @SerialName("explicit")  val isExplicit:Boolean,
    @SerialName("lyrics_body") val lyrics:String,
    @SerialName("lyrics_language") val language:String = "US",
    @SerialName("script_tracking_url") val scriptTrackingURL:String = "",
    @SerialName("pixel_tracking_url") val pixelTrackingURL:String = "",
    @SerialName("lyrics_copyright") val copyRight:String,
    @SerialName("backlink_url") val backLinkURL:String = "",
    @SerialName("updated_time") val updatedTime:String,
)