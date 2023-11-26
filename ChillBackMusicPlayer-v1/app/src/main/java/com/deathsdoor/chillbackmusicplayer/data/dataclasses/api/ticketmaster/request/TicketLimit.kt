package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.google.gson.annotations.SerializedName

data class TicketLimit(

    @field:SerializedName("info")
    val info: String
)