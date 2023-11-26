package com.deathsdoor.musicmatch.dataclasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
data class MusixTrack(
    @SerialName("track_id") val id: Int,
    @SerialName("track_name") val name: String,
    @SerialName("track_rating") val rating: Int,
    @SerialName("num_favourite") val numberMalAddedToFavouriteByMusicMatchUsers: Int,
    @SerialName("commontrack_id") val commonTrackId: Int,
    @SerialName("instrumental")  val isInstrumental: Boolean,
    @SerialName("explicit")  val isExplicit: Boolean,
    @SerialName("has_lyrics")  val hasLyrics: Boolean,
    @SerialName("has_subtitles")  val hasSubtitles: Boolean,
    @SerialName("has_richsync")  val hasRichsync: Boolean,
    @SerialName("album_id") val albumId: Int,
    @SerialName("album_name") val albumName: String,
    @SerialName("artist_id") val artistId: Int,
    @SerialName("artist_name") val artistName: String,
    @SerialName("track_share_url") val shareUrl: String,
    @SerialName("track_edit_url") val editUrl: String,
    @SerialName("restricted")  val isRestricted: Boolean,
    @SerialName("updated_time") val updatedTime: String,
    @SerialName("primary_genres") val genres: List<MusixGenre>,
    @SerialName("track_name_translation_list") val translatedNames : List<MusixTranslation>
)