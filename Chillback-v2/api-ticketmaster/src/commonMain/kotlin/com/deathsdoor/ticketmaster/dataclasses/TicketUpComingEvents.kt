package com.deathsdoor.ticketmaster.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class TicketUpComingEvents(
    val total : Int,
    val filtered : Int,
    val ticketmaster : Int,
    val ticketmasterResale :Int,
    //val mobileFanExperience : Map<String,Int>? = null
)