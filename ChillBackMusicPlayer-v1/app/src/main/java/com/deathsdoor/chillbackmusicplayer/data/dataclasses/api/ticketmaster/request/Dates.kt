package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.Start
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.Status
import com.google.gson.annotations.SerializedName

data class Dates(

    @field:SerializedName("start")
    val start: Start,

    @field:SerializedName("timezone")
    val timezone: String,


    @field:SerializedName("status")
    val status: Status,

    @field:SerializedName("spanMultipleDays")
    val spanMultipleDays: Boolean
)