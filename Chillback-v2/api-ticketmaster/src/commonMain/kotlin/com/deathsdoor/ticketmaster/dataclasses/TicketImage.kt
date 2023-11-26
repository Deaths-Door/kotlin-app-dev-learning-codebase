package com.deathsdoor.ticketmaster.dataclasses

import com.deathsdoor.ticketmaster.enums.TicketRatio
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketImage(
    val url : String,
    val ratio : TicketRatio,
    val width: Int,
    val height: Int,
    @SerialName("fallback") val isFallbackImage: Boolean,
    val attribution : String
){
    internal companion object {
        const val arrayName : String = "images"
    }
}


