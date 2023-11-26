package com.deathsdoor.ticketmaster.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class TicketAddress(
    val street: String,
    val city: String,
    val state: String
)