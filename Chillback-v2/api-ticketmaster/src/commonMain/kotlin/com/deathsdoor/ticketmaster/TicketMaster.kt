package com.deathsdoor.ticketmaster

import com.deathsdoor.request.utilities.RequestHandler
import com.deathsdoor.request.utilities.extensions.deserialize
import com.deathsdoor.request.utilities.extensions.deserializeList
import com.deathsdoor.ticketmaster.dataclasses.GeographicCoordinates
import com.deathsdoor.ticketmaster.dataclasses.TicketAttraction
import com.deathsdoor.ticketmaster.dataclasses.TicketClassification
import com.deathsdoor.ticketmaster.dataclasses.TicketEvent
import com.deathsdoor.ticketmaster.dataclasses.TicketGenre
import com.deathsdoor.ticketmaster.dataclasses.TicketImage
import com.deathsdoor.ticketmaster.dataclasses.TicketSegment
import com.deathsdoor.ticketmaster.dataclasses.TicketSubGenre
import com.deathsdoor.ticketmaster.dataclasses.TicketVenue
import com.deathsdoor.ticketmaster.enums.SortOrder
import com.deathsdoor.ticketmaster.enums.Source
import com.deathsdoor.ticketmaster.enums.TicketUnit
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

//TODO remove this afterwards
@Suppress("UNUSED","UNUSED_PARAMETER")
object
TicketMaster {
    private const val BASE_URL = "https://app.ticketmaster.com"
    class Discovery(apiKey : String) : RequestHandler() {
         override val baseURL: String = "$BASE_URL/discovery/v2/"
         override val apiKey : Pair<String,String> = "apiKey" to apiKey

         override fun concentrateBaseUndEndpoint(endpoint: String): String = "$baseURL/$endpoint.json?"
         override suspend fun HttpResponse.responseMessage(): JsonObject = json.decodeFromString(bodyAsText())
         override suspend fun HttpResponse.responseBody(): JsonObject? = responseMessage()["_embedded"]?.jsonObject
         suspend fun eventImages(id: String, locale: String?, domain: List<String>?): List<TicketImage>? = performDefaultAction(
             endpoint = "events/$id/images",
             parameters = mapOf(
                "id" to id,
                "locale" to locale,
                "domain" to domain
             ),
             deserializer = {
                 return@performDefaultAction it?.deserializeList<TicketImage>(TicketImage.arrayName)
             }
         )

         suspend fun venueDetails(id: String, locale: String?, domain: List<String>?): TicketVenue? = performDefaultAction(
             endpoint = "venues/$id",
             parameters = mapOf(
                "id" to id,
                "locale" to locale,
                "domain" to domain
             ),
             deserializer = {
                 return@performDefaultAction it?.deserialize<TicketVenue>()
             }
         )

         suspend fun genreDetails(id: String, locale: String?, domain: List<String>?)= performDefaultAction(
             endpoint = "classifications/genres/$id",
             parameters = mapOf(
                "id" to id,
                "locale" to locale,
                "domain" to domain
             ),
             deserializer = {
                 return@performDefaultAction it?.deserialize<TicketGenre>() to it?.deserializeList<TicketSubGenre>(TicketSubGenre.arrayName)
             }
         )

         suspend fun subGenreDetails(id: String, locale: String?, domain: List<String>?): TicketSubGenre? = performDefaultAction(
             endpoint = "classifications/subgenres/$id",
             parameters = mapOf(
                "id" to id,
                "locale" to locale,
                "domain" to domain
             ),
             deserializer = {
                return@performDefaultAction it?.deserialize<TicketSubGenre>()
             }
         )

         suspend fun segmentDetails(id: String, locale: String?, domain: List<String>?) : TicketSegment? = performDefaultAction(
             endpoint = "classifications/segments/$id",
             parameters = mapOf(
                "id" to id,
                "locale" to locale,
                "domain" to domain
             ),
             deserializer = {
                return@performDefaultAction it?.deserialize<TicketSegment>()
             }
         )

         suspend fun classificationDetails(
            id: String,
            locale: String?,
            domain: List<String>?
        ) : TicketClassification? = performDefaultAction(
            endpoint = "classifications/$id",
            parameters = mapOf(
                "id" to id,
                "locale" to locale,
                "domain" to domain
            ),
            deserializer = {
               return@performDefaultAction it?.deserialize<TicketClassification>()
            }
        )

        suspend fun attractionDetails(id: String, locale: String?, domain: List<String>?) : TicketAttraction? = performDefaultAction(
            endpoint = "attractions/$id",
            parameters = mapOf(
                "id" to id,
                "locale" to locale,
                "domain" to domain
            ),
            deserializer = {
               return@performDefaultAction it?.deserialize<TicketAttraction>()
            }
        )

        suspend fun eventDetails(id: String, locale: String?, domain: List<String>?) : TicketEvent? = performDefaultAction(
            endpoint = "events/$id",
            parameters = mapOf(),
            deserializer = {
               return@performDefaultAction it?.deserialize<TicketEvent>()
            }
        )

         suspend fun searchEvents(
            id: String?,
            keyword: String?,
            attractionId: String?,
            venueId: String?,
            postalCode: String?,
            radius: Int?,
            unit: TicketUnit?,
            source: Source?,
            marketId: String?,
            startDateTime: String?,
            endDateTime: String?,
            includeTBA: Boolean?,
            includeTBD: Boolean?,
            size: Int?,
            page: Int?,
            sortOrder: SortOrder?,
            onSaleStartDateTime: String?,
            onSaleEndDateTime: String?,
            city: Array<String>?,
            countryCode: String?,
            stateCode: String?,
            classificationId: Array<String>?,
            dmaId: String?,
            localStartDateTime: Array<String>?,
            localStartEndDateTime: Array<String>?,
            startEndDateTime: Array<String>?,
            publicVisibilityStartDateTime: Array<String>?,
            preSaleDateTime: Array<String>?,
            onSaleOnStartDate: String?,
            onSaleOnAfterStartDate: String?,
            collectionId: Array<String>?,
            segmentId: Array<String>?,
            segmentName: Array<String>?,
            includeFamily: Boolean?,
            promoterId: String?,
            genreId: Array<String>?,
            subGenreId: Array<String>?,
            typeId: Array<String>?,
            subTypeId: Array<String>?,
            geographicCoordinates: GeographicCoordinates?,
            preferredCountry: String?,
            includeSpellcheck: String?,
            domain: Array<String>?
        ) : List<TicketEvent> = performDefaultAction(
            endpoint = "events",
            parameters = mapOf(),
            deserializer = {
               return@performDefaultAction emptyList()//it?.deserialize<TicketEvents>()
            }
        ) ?: emptyList()

         suspend fun searchAttractions(
            id: String?,
            keyword: String?,
            source: Source?,
            locale: String?,
            size: Int?,
            page: Int?,
            sortOrder: SortOrder?,
            classificationName: Array<String>?,
            classificationId: Array<String>?,
            includeFamily: Boolean?,
            genreId: Array<String>?,
            subGenreId: Array<String>?,
            typeId: Array<String>?,
            subTypeId: Array<String>?,
            segmentId: Array<String>?,
            preferredCountry: String?,
            includeSpellcheck: String?,
            domain: Array<String>?
        ) : List<TicketAttraction> = performDefaultAction(
            endpoint = "attractions",
            parameters = mapOf(),
            deserializer = {
               return@performDefaultAction null //it?.deserialize<TicketEvents>()
            }
        ) ?: emptyList()
    }
}