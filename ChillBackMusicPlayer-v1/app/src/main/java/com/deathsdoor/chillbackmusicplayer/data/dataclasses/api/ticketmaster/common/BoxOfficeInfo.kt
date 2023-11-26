package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class BoxOfficeInfo(

    @field:SerializedName("openHoursDetail")
    val openHoursDetail: String,

    @field:SerializedName("phoneNumberDetail")
    val phoneNumberDetail: String,

    @field:SerializedName("willCallDetail")
    val willCallDetail: String,
)