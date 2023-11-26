package com.deathsdoor.ticketmaster.dataclasses

import kotlinx.serialization.Serializable


@Serializable
data class TicketLocation (
    val area : String,
    val cityName: String,
    val stateName : String,
    val stateCode : String,
    val countryName : String,
    val countryCode : String,
    val geographicCoordinate : GeographicCoordinates,
    val postalCode : String,
    val timezone : String,
    val address: TicketAddress,
)