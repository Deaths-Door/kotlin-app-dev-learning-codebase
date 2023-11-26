package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.google.gson.annotations.SerializedName

data class DmasItem(

    @field:SerializedName("id")
    val id: Int
)