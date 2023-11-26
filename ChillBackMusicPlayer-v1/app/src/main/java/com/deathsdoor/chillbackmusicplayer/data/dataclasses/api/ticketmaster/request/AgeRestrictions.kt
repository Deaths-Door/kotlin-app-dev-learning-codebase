package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.google.gson.annotations.SerializedName

data class AgeRestrictions(

    @field:SerializedName("legalAgeEnforced")
    val legalAgeEnforced: Boolean
)