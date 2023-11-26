package com.deathsdoor.musicmatch

import com.deathsdoor.musicmatch.dataclasses.MusixLyrics
import com.deathsdoor.musicmatch.dataclasses.MusixTrack
import com.deathsdoor.request.utilities.RequestHandler
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject


class MusixAbgleich constructor(apiKey : String) : RequestHandler(){
    override val baseURL: String = "https://api.musixmatch.com/ws/1.1/"
    override val apiKey: Pair<String,String> = "apiKey" to apiKey

    override suspend fun HttpResponse.responseMessage(): JsonObject? = json.decodeFromString<JsonObject>(bodyAsText())["message"]?.jsonObject

    suspend fun searchTracks(
        title: String? = null,
        artistName: String? = null,
        lyricsKeyword: String? = null,
        trackArtistKeyword: String? = null,
        writerKeyword: String? = null,
        generalKeyword: String? = null,
        artistIdFilter: String? = null,
        musicGenreIdFilter: String? = null,
        lyricsLanguageFilter: String? = null,
        hasLyricsFilter: Boolean? = null,
        releaseDateMinFilter: String? = null,
        releaseDateMaxFilter: String? = null,
        sortByArtistRating: String? = null,
        sortByTrackRating: String? = null,
        quorumFactor: Double? = null,
        pageNumber: Int? = null,
        pageSize: Int? = null
    ) : List<MusixTrack> = performDefaultAction(
        endpoint = "track.lyrics.get",
        parameters = mapOf(
            "q_track" to title,
            "q_artist" to artistName,
            "q_lyrics" to lyricsKeyword,
            "q_track_artist" to trackArtistKeyword,
            "q_writer" to writerKeyword,
            "q" to generalKeyword,
            "f_artist_id" to artistIdFilter,
            "f_music_genre_id" to musicGenreIdFilter,
            "f_lyrics_language" to lyricsLanguageFilter,
            "f_has_lyrics" to hasLyricsFilter,
            "f_track_release_group_first_release_date_min" to releaseDateMinFilter,
            "f_track_release_group_first_release_date_max" to releaseDateMaxFilter,
            "s_artist_rating" to sortByArtistRating,
            "s_track_rating" to sortByTrackRating,
            "quorum_factor" to quorumFactor,
            "page" to pageNumber,
            "page_size" to pageSize
        ),
        deserializer = {
            null
        }
    ) ?: emptyList()

    suspend fun trackLyricsWithCommonTrackID(id : Int) : MusixLyrics? = performDefaultAction(
        endpoint = "track.lyrics.get",
        parameters = mapOf(
            "track_id" to id
        ),
        deserializer = {
            null
        }
    )
    suspend fun trackLyricsWithTrackID(id : Int) : MusixLyrics? = performDefaultAction(
        endpoint = "track.lyrics.get",
        parameters = mapOf(
            "commontrack_id" to id
        ),
        deserializer = {
            null
        }
    )
}