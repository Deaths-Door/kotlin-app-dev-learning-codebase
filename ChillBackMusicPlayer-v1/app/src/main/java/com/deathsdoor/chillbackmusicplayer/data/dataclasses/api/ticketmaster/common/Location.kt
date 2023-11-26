package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class Location(

    @field:SerializedName("latitude")
    val latitude: String,

    @field:SerializedName("longitude")
    val longitude: String
)