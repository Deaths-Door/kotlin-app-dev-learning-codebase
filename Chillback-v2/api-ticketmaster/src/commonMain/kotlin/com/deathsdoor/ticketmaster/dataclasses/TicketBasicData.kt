package com.deathsdoor.ticketmaster.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class TicketBasicData(
    val id: String,
    val name: String,
    val description : String,
    val additionalInfo : String,
    val url: String? = null,
    val locale: String,
)