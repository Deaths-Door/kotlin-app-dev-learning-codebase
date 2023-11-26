package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.Links
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.Classification
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.UpcomingEvents
import com.google.gson.annotations.SerializedName

data class AttractionsItem(

    @field:SerializedName("href")
    val href: String,

    @field:SerializedName("classifications")
    val classifications: List<Classification>,

    @field:SerializedName("images")
    val images: List<Image>,

    @field:SerializedName("aliases")
    val aliases: List<String>,

    @field:SerializedName("test")
    val test: Boolean,

    @field:SerializedName("_links")
    val links: Links,

    @field:SerializedName("upcomingEvents")
    val upcomingEvents: UpcomingEvents,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("externalLinks")
    val externalLinks: ExternalLinks,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("locale")
    val locale: String,

    @field:SerializedName("url")
    val url: String
)