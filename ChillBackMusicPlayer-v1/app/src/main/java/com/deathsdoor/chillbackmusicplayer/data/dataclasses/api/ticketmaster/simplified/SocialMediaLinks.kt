package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.simplified

import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request.ExternalLinks

data class SocialMediaLinks(
    val twitter: List<String>,
    val wiki: List<String>,
    val facebook: List<String>,
    val instagram: List<String>,
    val homepage: List<String>,
    val musicbrainz: List<String>,
    val youtube: List<String>,
    val itunes: List<String>,
    val lastfm: List<String>,
    val spotify: List<String>
) {

    constructor(it: ExternalLinks) : this(
        it.twitter.map { it.url },
        it.wiki.map { it.url },
        it.facebook.map { it.url },
        it.instagram.map { it.url },
        it.homepage.map { it.url },
        it.musicbrainz.map { it.url },
        it.youtube.map { it.url },
        it.itunes.map { it.url },
        it.lastfm.map { it.url },
        it.spotify.map { it.url },
        ){
    }
}