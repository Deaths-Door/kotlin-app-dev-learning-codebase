package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.google.gson.annotations.SerializedName

data class Ticketing(

    @field:SerializedName("safeTix")
    val safeTix: SafeTix
)