package com.deathsdoor.chillbackmusicplayer.data.api

import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.simplified.EventInfo
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.TicketMasterResponse
import com.deathsdoor.chillbackmusicplayer.data.extensions.debugLog
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.RetrofitWrapper
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@Deprecated("use from repo")
class TicketMaster(private val apikey: String) {
    private companion object{
        private const val baseURL = "https://app.ticketmaster.com/discovery/v2/"
        private val retrofit = RetrofitWrapper(baseURL,Api::class.java)
        private val service = retrofit.service
    }

    enum class Source(val string: String) {
        TICKETMASTER("ticketmaster"),
        UNIVERSE("universe"),
        FRONTGATE("frontgate"),
        TMR("tmr")
    }
    //TODO add to discoverevents
    /*
        * classificationName, classificationId , dmaId,localStartDateTime ,, localStartEndDateTime
        startEndDateTime , publicVisibilityStartDateTime, preSaleDateTime , onsaleOnStartDate ,onsaleOnAfterStartDate,
        collectionId, ,segmentId , segmentName , includeFamily , promoterId , genreId , subGenreId , typeId,subTypeId ,
        * geoPoint ,preferredCountry, includeSpellcheck , domain

        *
    * */
    private interface Api{
        @GET("events.json")
        fun discoverEvents(
            @Query("apikey") apiKey: String, @Query("id") entitiesID: String?,
            @Query("attractionId") attractionId: String?, @Query("venueId") venueId: String?,
            @Query("marketId") marketId: String?, @Query("keyword") keyword: String?,
            @Query("radius") radius: Int?, @Query("unit") unit: String?,
            @Query("postalCode") postalCode: String?, @Query("source") source: String?,
            @Query("locale") locale: String?, @Query("startDateTime") startDateTime: String?,
            @Query("endDateTime") endDateTime: String?, @Query("includeTBA") includeTBA: String?,
            @Query("includeTBD") includeTBD: String?, @Query("includeTest") includeTest: String?,
            @Query("size") size: String?, @Query("page") page: String?,
            @Query("sort") sort: String?, @Query("onsaleStartDateTime") onsaleStartDateTime: String?,
            @Query("onsaleEndDateTime") onsaleEndDateTime: String?, @Query("city") cities: List<String>?,
            @Query("countryCode") countryCode: String?, @Query("stateCode") stateCode: String?
        ): Call<TicketMasterResponse>


        //TODO add domain parm to it
        @GET("events/{id}.json")
        fun eventDetails(
            @Path ("id") id: String,
            @Query("apikey") apiKey: String,
            @Query("locale") locale: String?,
        ):Call<TicketMasterResponse>
    }

    private fun constructStringFromLocale(locales: List<String>?): String? = locales?.joinToString { "," }
    //TODO Complete the endpoint add all the parms and create interface for it
    fun discoverEvents(
        entitiesID:String? = null, attractionId:String? = null,
        venueId:String? = null, marketId:String? = null,
        keyword:String? = null, radius: Int? = null,
        useMetricSystem:Boolean = true, source:Source? = null,
        locales:List<String>? = null, postalCode: String? = null,
        startDateTime:String? = null): TicketMasterResponse? {
        var data : TicketMasterResponse? = null
        runBlocking {
            //TODO Complete the endpoint
            val request = service.discoverEvents(
                apikey,
                entitiesID,attractionId,venueId,marketId,
                keyword,radius, unit = if(useMetricSystem) "km" else "miles", postalCode,
                source?.string,constructStringFromLocale(locales),
                null,null,
                null,null,null,
                null,null,
                null,
                null,null,
                null,null,null,
            )
            retrofit.makeSyncRequest(request,object:RetrofitWrapper.OnRequestMade<TicketMasterResponse>{
                override fun onSuccess(response: Response<TicketMasterResponse>, body: TicketMasterResponse?) {
                    data = response.body()
                    "TicketMasterResponse".debugLog("onSuccess Single: $body")
                }
                override fun onError(response: Response<TicketMasterResponse>, errorBody: ResponseBody?) {
                    "TicketMasterResponse".debugLog("onError Single: ${errorBody?.string()}")
                }
                override fun onFailure(call: Call<TicketMasterResponse>, throwable: Throwable) {
                    "TicketMasterResponse".debugLog("onFailure Single: ${throwable.message}")
                }
            })
        }
        return data
    }
    fun eventDetails(eventID:String,locales:List<String>? = null): List<EventInfo>? {
        var data : TicketMasterResponse? = null
        runBlocking {
            //TODO Complete the endpoint
            val request = service.eventDetails(eventID,apikey,constructStringFromLocale(locales))
            retrofit.makeSyncRequest(request,object:RetrofitWrapper.OnRequestMade<TicketMasterResponse>{
                override fun onSuccess(response: Response<TicketMasterResponse>, body: TicketMasterResponse?) {
                    data = body
                    "TicketMasterResponse".debugLog("onSuccess Single: $body")
                }
                override fun onError(response: Response<TicketMasterResponse>, errorBody: ResponseBody?) {
                    "TicketMasterResponse".debugLog("onError Single: ${errorBody?.string()}")
                }
                override fun onFailure(call: Call<TicketMasterResponse>, throwable: Throwable) {
                    "TicketMasterResponse".debugLog("onFailure Single: ${throwable.message}")
                }
            })
        }
        return data?.embedded?.events?.let { list -> list.map { EventInfo(it) } }
    }
}