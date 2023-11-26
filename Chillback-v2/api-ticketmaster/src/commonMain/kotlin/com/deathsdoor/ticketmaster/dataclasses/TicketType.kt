package com.deathsdoor.ticketmaster.dataclasses

import com.deathsdoor.ticketmaster.internal.Value
import kotlinx.serialization.Serializable

@Serializable
data class TicketType(
    override val id: String,
    override val name: String,
    override val locale: String,
    val subtypes : TicketSubType
) : Value
