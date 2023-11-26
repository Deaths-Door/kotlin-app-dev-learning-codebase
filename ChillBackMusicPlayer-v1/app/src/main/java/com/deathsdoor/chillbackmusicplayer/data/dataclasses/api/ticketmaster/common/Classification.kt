package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.IdentifiableName
import com.google.gson.annotations.SerializedName

data class Classification(

    @field:SerializedName("subGenre")
    val subGenre: IdentifiableName,

    @field:SerializedName("segment")
    val segment: IdentifiableName,

    @field:SerializedName("genre")
    val genre: IdentifiableName,

    @field:SerializedName("subType")
    val subType: IdentifiableName,

    @field:SerializedName("type")
    val type: IdentifiableName,

    @field:SerializedName("family")
    val family: Boolean,

    @field:SerializedName("primary")
    val primary: Boolean
)