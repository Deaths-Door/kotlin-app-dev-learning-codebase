package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.google.gson.annotations.SerializedName

data class SafeTix(

    @field:SerializedName("enabled")
    val enabled: Boolean
)