package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request

import com.google.gson.annotations.SerializedName

data class ExternalLinks(

    @field:SerializedName("twitter")
    val twitter: List<Link>,

    @field:SerializedName("wiki")
    val wiki: List<Link>,

    @field:SerializedName("facebook")
    val facebook: List<Link>,

    @field:SerializedName("instagram")
    val instagram: List<Link>,

    @field:SerializedName("homepage")
    val homepage: List<Link>,

    @field:SerializedName("musicbrainz")
    val musicbrainz: List<Link>,

    @field:SerializedName("youtube")
    val youtube: List<Link>,

    @field:SerializedName("itunes")
    val itunes: List<Link>,

    @field:SerializedName("lastfm")
    val lastfm: List<Link>,

    @field:SerializedName("spotify")
    val spotify: List<Link>
)