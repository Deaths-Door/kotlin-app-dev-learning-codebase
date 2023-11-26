package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class Accessibility(

    @field:SerializedName("ticketLimit")
    val ticketLimit: Int,

    @field:SerializedName("info")
    val info: String
)