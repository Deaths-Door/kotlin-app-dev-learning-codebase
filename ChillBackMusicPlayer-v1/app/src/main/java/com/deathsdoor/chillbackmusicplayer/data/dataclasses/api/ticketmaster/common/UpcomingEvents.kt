package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class UpcomingEvents(

    @field:SerializedName("_total")
    val total: Int,

    @field:SerializedName("ticketmaster")
    val ticketmaster: Int,

    @field:SerializedName("_filtered")
    val filtered: Int,
)