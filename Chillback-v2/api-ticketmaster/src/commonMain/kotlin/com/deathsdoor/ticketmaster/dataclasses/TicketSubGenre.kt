package com.deathsdoor.ticketmaster.dataclasses

import com.deathsdoor.ticketmaster.internal.Value
import kotlinx.serialization.Serializable

@Serializable
data class TicketSubGenre(
    override val id: String,
    override val name: String,
    override val locale: String
) : Value {
    internal companion object {
        const val arrayName = "subGenres"
    }
}

