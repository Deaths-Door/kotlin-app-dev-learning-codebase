package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class Country(

    @field:SerializedName("countryCode")
    val countryCode: String,

    @field:SerializedName("name")
    val name: String
)