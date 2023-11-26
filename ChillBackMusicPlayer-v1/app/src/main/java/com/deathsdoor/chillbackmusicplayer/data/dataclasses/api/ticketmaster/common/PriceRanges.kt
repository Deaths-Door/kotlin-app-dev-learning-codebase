package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class PriceRanges(

    @field:SerializedName("min")
    val min: Any,

    @field:SerializedName("max")
    val max: Any,

    @field:SerializedName("currency")
    val currency: String,

    @field:SerializedName("type")
    val type: String
)