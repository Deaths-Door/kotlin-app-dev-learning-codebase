package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.simplified

import android.net.Uri
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.Classification
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.UpcomingEvents
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request.AttractionsItem
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request.ExternalLinks
import com.deathsdoor.chillbackmusicplayer.data.extensions.Image.highestResolutionImage

data class Attraction(
    val name: String,
    val id: String,
    val requireCovidTest:Boolean,
    val websiteURL: String,
    val locale: String,
    val image:Uri,
    val socialMediaLinks: SocialMediaLinks,
    val classifications: List<Classification>,
    val upcomingEvents: UpcomingEvents,
){
    constructor(it:AttractionsItem):this(
        it.name,
        it.id,
        it.test,
        it.url,
        it.locale,
        Uri.parse(it.images.highestResolutionImage().url),
        SocialMediaLinks(it.externalLinks),
        it.classifications,
        it.upcomingEvents
    )
}