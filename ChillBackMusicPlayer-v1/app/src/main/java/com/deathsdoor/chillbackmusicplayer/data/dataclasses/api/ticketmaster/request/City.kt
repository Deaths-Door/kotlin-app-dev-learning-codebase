package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.google.gson.annotations.SerializedName

data class City(

    @field:SerializedName("name")
    val name: String
)