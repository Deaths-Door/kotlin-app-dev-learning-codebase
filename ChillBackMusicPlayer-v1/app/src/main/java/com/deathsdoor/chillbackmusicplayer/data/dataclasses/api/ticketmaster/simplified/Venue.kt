package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.simplified

import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.*
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request.VenueItem

data class Venue(
    val name: String,
    val id: String,
    val requireCovidTest: Boolean,
    val website: String,
    val locale: String,
    val postalCode: String,
    val timezone: String,
    val parkingDetail: String,
    val accessibleSeatingDetail: String,
    val city: String,
    val state: State,
    val country: Country,
    val address: String,
    val location:Location,
    val markets: List<Market>,
    val designatedMarketAreas: List<Int>,
    val boxOfficeInfo: BoxOfficeInfo,
    val generalInfo: String,
    val upcomingEvents: UpcomingEvents,
    ){
    constructor(it:VenueItem):this(
        name = it.name,
        id =    it.id,
        requireCovidTest =   it.requireCovidTest,
        website =  it.website,
        locale =   it.locale,
        postalCode =   it.postalCode,
        timezone = it.timezone,
        parkingDetail =  it.parkingDetail,
        accessibleSeatingDetail =   it.accessibleSeatingDetail,
        city =  it.city.name,
        state =  it.state,
        country = it.country,
        address =   it.address.line1,
        location = it.location,
        markets = it.markets,
        designatedMarketAreas = it.dmas.map { it.id },
        boxOfficeInfo = it.boxOfficeInfo,
        generalInfo = it.generalInfo.childRule,
        upcomingEvents = it.upcomingEvents
    )
}
