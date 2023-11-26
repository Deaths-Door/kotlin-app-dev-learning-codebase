package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.google.gson.annotations.SerializedName

data class Image(

    @field:SerializedName("width")
    val width: Int,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("height")
    val height: Int,
)