package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class State(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("stateCode")
    val stateCode: String
)