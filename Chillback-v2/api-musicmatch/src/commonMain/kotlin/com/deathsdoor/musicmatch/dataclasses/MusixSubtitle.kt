package com.deathsdoor.musicmatch.dataclasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusixSubtitle(
    @SerialName("subtitle_id") val subtitleId: Int,
    @SerialName("restricted") val isRestricted: Boolean,
    //TODO change type base on format used
    //TODO change from string into list
    @SerialName("subtitle_body") val subtitleBody: String,
    @SerialName("subtitle_language") val subtitleLanguage: String,
    @SerialName("script_tracking_url") val scriptTrackingUrl: String,
    @SerialName("pixel_tracking_url") val pixelTrackingUrl: String,
    @SerialName("html_tracking_url") val htmlTrackingUrl: String,
    @SerialName("lyrics_copyright") val lyricsCopyright: String
)