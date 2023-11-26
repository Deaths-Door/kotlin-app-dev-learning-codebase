package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.Embedded
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.*
import com.google.gson.annotations.SerializedName

data class VenueItem(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("test")
    val requireCovidTest: Boolean,

    @field:SerializedName("url")
    val website: String,

    @field:SerializedName("locale")
    val locale: String,

    @field:SerializedName("postalCode")
    val postalCode: String,

    @field:SerializedName("timezone")
    val timezone: String,

    @field:SerializedName("parkingDetail")
    val parkingDetail: String,

    @field:SerializedName("accessibleSeatingDetail")
    val accessibleSeatingDetail: String,

    @field:SerializedName("city")
    val city: City,

    @field:SerializedName("state")
    val state: State,

    @field:SerializedName("country")
    val country: Country,

    @field:SerializedName("address")
    val address: Address,

    @field:SerializedName("location")
    val location: Location,

    @field:SerializedName("markets")
    val markets: List<Market>,


    @field:SerializedName("dmas")
    val dmas: List<DmasItem>,

    @field:SerializedName("boxOfficeInfo")
    val boxOfficeInfo: BoxOfficeInfo,

    @field:SerializedName("generalInfo")
    val generalInfo: GeneralInfo,

    @field:SerializedName("upcomingEvents")
    val upcomingEvents: UpcomingEvents,
)