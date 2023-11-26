package com.deathsdoor.ticketmaster.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class TicketAliases(
    val aliases: List<String>,
    val localized : List<String>
)
