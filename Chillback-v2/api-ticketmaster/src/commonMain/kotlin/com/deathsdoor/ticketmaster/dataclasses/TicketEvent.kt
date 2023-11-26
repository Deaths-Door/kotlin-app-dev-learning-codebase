package com.deathsdoor.ticketmaster.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class TicketEvent(
    val basicData: TicketBasicData,
    val distance : Double,
    val geographicCoordinates: GeographicCoordinates,
    val images : List<TicketImage>,
    val aliases: TicketAliases,
    val externalLinks: TicketExternalLinks,
    val location: TicketLocation,
    val classifications : List<TicketClassification>,
    val saleInfo: TicketSaleInfo
)