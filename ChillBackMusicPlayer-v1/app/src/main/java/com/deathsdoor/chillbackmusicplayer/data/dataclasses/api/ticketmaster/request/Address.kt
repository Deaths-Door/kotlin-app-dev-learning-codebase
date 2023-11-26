package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.google.gson.annotations.SerializedName

data class Address(

    @field:SerializedName("line1")
    val line1: String,

    @field:SerializedName("line2")
    val line2: String
)