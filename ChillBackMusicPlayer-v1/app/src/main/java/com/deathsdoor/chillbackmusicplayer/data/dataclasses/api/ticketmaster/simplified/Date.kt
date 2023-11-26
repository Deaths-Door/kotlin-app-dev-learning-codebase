package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.simplified

import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request.Dates
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.Start

data class Date(
    val start: Start,
    val timezone: String,
    val status: String,
    val spansMultipleDays: Boolean
){
    constructor(data: Dates) : this(data.start,data.timezone,data.status.code,data.spanMultipleDays)
}