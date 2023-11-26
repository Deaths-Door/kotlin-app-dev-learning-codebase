package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class PreSales(

    @field:SerializedName("startDateTime")
    val startDateTime: String,

    @field:SerializedName("endDateTime")
    val endDateTime: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,
)