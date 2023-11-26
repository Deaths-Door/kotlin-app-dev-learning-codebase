package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class PublicSales(

    @field:SerializedName("startDateTime")
    val startDateTime: String,

    @field:SerializedName("startTBD")
    val startTBD: Boolean,

    @field:SerializedName("endDateTime")
    val endDateTime: String,

    @field:SerializedName("startTBA")
    val startTBA: Boolean
)