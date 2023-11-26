package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class Product(

    @field:SerializedName("classifications")
    val classifications: List<Classification>,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("url")
    val websiteUrl: String
)