package com.deathsdoor.ticketmaster.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class TicketClassification(
    val isPrimary: Boolean,
    val isFamilyFriendly : Boolean,
    val segment: TicketSegment,
    val genre: TicketGenre,
    val subGenre: TicketSubGenre,
    val type : TicketType,
    val subtype : TicketSubType
)