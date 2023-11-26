package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.simplified

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request.AttractionsItem
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.EventsItem
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.*
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request.Image
import com.deathsdoor.chillbackmusicplayer.data.extensions.Image.highestResolutionImage

//TODO finsih it
@Entity
data class EventInfo(
    val name:String,
    val image: Uri,
    val requireCovidTest:Boolean,
    val eventWebsite:String,
    val locale:String,
    val requestNote:String,
    val sales: Sales,
    val dates: Date,
    val classifications: List<Classification>,
    val promoter: Promoter,
    //val promoters: List<PromotersItem>,
    val priceRanges: List<PriceRanges>,
    val products: List<Product>?,
    val seatMap:Uri,
    val accessibility: Accessibility,
    val ticketLimitInfo:String,
    val isLegalAgePolicyEnforced: Boolean,
    val ticketMasterSafeTixEnables:Boolean,
    val venues:List<Venue>?,
    val attractions: List<Attraction>?,
    @PrimaryKey(autoGenerate = false) val eventID:String,
    ) {
    constructor(response: EventsItem):this(
        eventID =  response.id,
        name =  response.name,
        image = Uri.parse(response.images.highestResolutionImage().url),
        requireCovidTest = response.test,
        eventWebsite = response.url,
        locale = response.locale,
        requestNote = response.pleaseNote,
        sales = response.sales,
        dates = Date(response.dates),
        classifications = response.classifications,
        promoter = response.promoter,
        priceRanges = response.priceRanges,
        products = response.products,
        seatMap = Uri.parse(response.seatmap.staticUrl),
        accessibility = response.accessibility,
        ticketLimitInfo = response.ticketLimit.info,
        isLegalAgePolicyEnforced = response.ageRestrictions.legalAgeEnforced,
        ticketMasterSafeTixEnables = response.ticketing.safeTix.enabled,
        venues = response.embedded.venues?.map { Venue(it) },
        attractions = response.embedded.attractions?.map { Attraction(it) }
    )
}
