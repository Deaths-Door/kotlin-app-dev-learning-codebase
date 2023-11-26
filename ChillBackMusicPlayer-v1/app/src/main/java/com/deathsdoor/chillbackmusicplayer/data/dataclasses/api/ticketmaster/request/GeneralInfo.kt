package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.google.gson.annotations.SerializedName

data class GeneralInfo(
    @field:SerializedName("childRule")
    val childRule: String
)