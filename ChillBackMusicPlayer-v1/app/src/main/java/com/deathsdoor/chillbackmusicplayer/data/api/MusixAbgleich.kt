package com.deathsdoor.chillbackmusicplayer.data.api

import com.deathsdoor.chillbackmusicplayer.data.extensions.debugLog
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.RetrofitWrapper
import com.deathsdoor.chillbackmusicplayer.test.MusixMatchResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*
*   chart.artists.get
    chart.tracks.get
    track.search
    track.get
    track.lyrics.get
    track.lyrics.post
    track.lyrics.mood.get
    track.snippet.get
    track.subtitle.get
    track.richsync.get
    track.lyrics.translation.get
    track.subtitle.translation.get
    music.genres.get
    matcher.lyrics.get
    matcher.track.get
    matcher.subtitle.get
    artist.get
    artist.search
    artist.albums.get
    artist.related.get
    album.get
    album.tracks.get
    tracking.url.get
    catalogue.dump.get
    work.post
*/
class MusixAbgleich(private val apiKey: String) {
    companion object{
        private const val baseURL = "https://api.musixmatch.com/ws/1.1/"
        private val retrofit = RetrofitWrapper(baseURL, Api::class.java)
        private val service = retrofit.service
    }
    //TODO add functions
    //TODO make counctyr enum
    //TODO change Call<TYPE>
    private interface Api{
        //TODO get custom dataclass for it
        @GET("chart.artists.get")
        fun getTopArtist(
            @Query("page") page :Int,
            @Query("page_size") pageSize :Int,
            @Query("country") country :String,
        ) : Call<MusixMatchResponse?>

        @GET("chart.artists.get")
        fun getTopArtistTest(
            @Query("page") page :Int,
            @Query("page_size") pageSize :Int,
            @Query("country") country :String,
        ) : Call<List<MusixMatchResponse>?>
    }

    fun getTopArtist(page :Int, pageSize :Int, country :String): MusixMatchResponse? {
        var data:MusixMatchResponse? = null
        retrofit.makeAsyncRequest(service.getTopArtist(page,pageSize,country),object :RetrofitWrapper.OnRequestMade<MusixMatchResponse?>{
            override fun onSuccess(response: Response<MusixMatchResponse?>, body: MusixMatchResponse?) {
                data = response.body()
                "MusicMatchResponse".debugLog("onSuccess Single: $body")
            }

            override fun onError(response: Response<MusixMatchResponse?>, errorBody: ResponseBody?) {
                "MusicMatchResponse".debugLog("onError Single: $errorBody")
            }

            override fun onFailure(call: Call<MusixMatchResponse?>, throwable: Throwable) {
                "MusicMatchResponse".debugLog("onFailure Single: ${throwable.message}")
            }
        })

        return data
    }

    fun getTopArtistTest(page :Int, pageSize :Int, country :String): List<MusixMatchResponse>? {
        var data: List<MusixMatchResponse>? = arrayListOf()
        retrofit.makeAsyncRequest(service.getTopArtistTest(page,pageSize,country),object :RetrofitWrapper.OnRequestMade<List<MusixMatchResponse>?>{
            override fun onSuccess(response: Response<List<MusixMatchResponse>?>, body:List<MusixMatchResponse>?) {
                "MusicMatchResponse".debugLog("onSuccess Array: $body")
                data = response.body()
            }

            override fun onError(response: Response<List<MusixMatchResponse>?>, errorBody: ResponseBody?) {
                "MusicMatchResponse".debugLog("onError Array: $errorBody")
            }

            override fun onFailure(call: Call<List<MusixMatchResponse>?>, throwable: Throwable) {
                "MusicMatchResponse".debugLog("onFailure Array: ${throwable.message}")
            }
        })

        return data
    }
}

