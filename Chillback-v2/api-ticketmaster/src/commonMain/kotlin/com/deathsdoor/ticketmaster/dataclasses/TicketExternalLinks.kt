package com.deathsdoor.ticketmaster.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class TicketExternalLinks(
    val twitter : List<String>,
    val wiki : List<String>,
    val facebook : List<String>,
    val instagram : List<String>,
    val homepage : List<String>
)