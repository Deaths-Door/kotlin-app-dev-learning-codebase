package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class IdentifiableName(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String
)
