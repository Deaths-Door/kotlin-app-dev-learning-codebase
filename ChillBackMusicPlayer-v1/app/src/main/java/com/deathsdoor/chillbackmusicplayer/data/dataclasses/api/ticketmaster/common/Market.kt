package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class Market(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String
)