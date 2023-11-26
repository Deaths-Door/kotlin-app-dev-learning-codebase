package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.google.gson.annotations.SerializedName

data class Seatmap(

    @field:SerializedName("staticUrl")
    val staticUrl: String
)