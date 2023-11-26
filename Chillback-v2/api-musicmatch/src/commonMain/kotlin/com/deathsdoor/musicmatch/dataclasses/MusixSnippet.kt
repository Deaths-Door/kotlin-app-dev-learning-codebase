package com.deathsdoor.musicmatch.dataclasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusixSnippet(
    @SerialName("snippet_language") val language: String,
    @SerialName("snippet_id") val id: Int,
    @SerialName("restricted") val isRestricted: Boolean,
    @SerialName("instrumental")  val isInstrumental: Boolean,
    @SerialName("snippet_body") val snippet_body: String,
    @SerialName("script_tracking_url") val script_tracking_url: String,
    @SerialName("pixel_tracking_url") val pixel_tracking_url: String,
    @SerialName("html_tracking_url") val html_tracking_url: String,
    @SerialName("updated_time") val updated_time: String
)