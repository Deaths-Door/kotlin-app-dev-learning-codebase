package com.deathsdoor.ticketmaster.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class TicketAttraction(
    val basicData: TicketBasicData,
    val images : List<TicketImage>,
    val classifications: List<TicketClassification>,
    val aliases: TicketAliases,
    val externalLinks: TicketExternalLinks,
    val upComingEvents: TicketUpComingEvents
)

