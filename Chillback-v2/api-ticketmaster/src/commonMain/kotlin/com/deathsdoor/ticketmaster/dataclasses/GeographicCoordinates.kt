package com.deathsdoor.ticketmaster.dataclasses

import kotlinx.serialization.Serializable

//TODO maybe convert to value class for optimizations
@Serializable
data class GeographicCoordinates(val longitude : Double, val latitude : Double)