package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster

import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common.*
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.request.*
import com.google.gson.annotations.SerializedName

data class TicketMasterResponse(

    @field:SerializedName("_embedded")
    val embedded: Embedded,

    //TODO useless field for me
    @field:SerializedName("_links")
    val links: Links,

    //TODO useless field for me
    @field:SerializedName("page")
    val page: Page
)

data class EventsItem(

    @field:SerializedName("images")
    val images: List<Image>,

    @field:SerializedName("test")
    val test: Boolean,

    @field:SerializedName("seatmap")
    val seatmap: Seatmap,

    @field:SerializedName("accessibility")
    val accessibility: Accessibility,

    @field:SerializedName("_links")
    val links: Links,

    @field:SerializedName("dates")
    val dates: Dates,

    @field:SerializedName("priceRanges")
    val priceRanges: List<PriceRanges>,

    @field:SerializedName("units")
    val units: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("locale")
    val locale: String,

    @field:SerializedName("ageRestrictions")
    val ageRestrictions: AgeRestrictions,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("sales")
    val sales: Sales,

    @field:SerializedName("classifications")
    val classifications: List<Classification>,

    @field:SerializedName("ticketLimit")
    val ticketLimit: TicketLimit,

    @field:SerializedName("ticketing")
    val ticketing: Ticketing,

    @field:SerializedName("_embedded")
    val embedded: Embedded,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("pleaseNote")
    val pleaseNote: String,

    @field:SerializedName("promoter")
    val promoter: Promoter,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("promoters")
    val promoters: List<PromotersItem>,

    //TODO : useless field i believe
    @field:SerializedName("products")
    val products: List<Product>?,

    @field:SerializedName("outlets")
    val outlets: List<OutletsItem>
)

data class Page(

    @field:SerializedName("number")
    val number: Int,

    @field:SerializedName("size")
    val size: Int,

    @field:SerializedName("totalPages")
    val totalPages: Int,

    @field:SerializedName("totalElements")
    val totalElements: Int
)

data class First(

    @field:SerializedName("href")
    val href: String
)

data class Next(

    @field:SerializedName("href")
    val href: String
)

data class Embedded(

    @field:SerializedName("events")
    val events: List<EventsItem>? = null,

    @field:SerializedName("venues")
    val venues: List<VenueItem>? = null,

    @field:SerializedName("attractions")
    val attractions: List<AttractionsItem>? = null
)

data class PromotersItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("id")
    val id: String
)

data class Self(

    @field:SerializedName("href")
    val href: String
)

data class Last(

    @field:SerializedName("href")
    val href: String
)

data class OutletsItem(

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("url")
    val url: String
)

data class Links(

    @field:SerializedName("next")
    val next: Next,

    @field:SerializedName("last")
    val last: Last,

    @field:SerializedName("self")
    val self: Self,

    @field:SerializedName("first")
    val first: First,

    @field:SerializedName("venues")
    val venues: List<VenueItem>,

    @field:SerializedName("attractions")
    val attractions: List<AttractionsItem>
)

data class Status(

    @field:SerializedName("code")
    val code: String
)

data class Start(

    @field:SerializedName("dateTime")
    val dateTime: String,

    @field:SerializedName("localTime")
    val localTime: String,

    @field:SerializedName("dateTBA")
    val dateTBA: Boolean,

    @field:SerializedName("noSpecificTime")
    val noSpecificTime: Boolean,

    @field:SerializedName("timeTBA")
    val timeTBA: Boolean,

    @field:SerializedName("localDate")
    val localDate: String,

    @field:SerializedName("dateTBD")
    val dateTBD: Boolean
)