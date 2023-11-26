package com.deathsdoor.musicmatch.dataclasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusixAlbum(
    @SerialName("album_id") val id: Int,
    @SerialName("album_mbid") val musicBrainzIdentifier: String?,

    @SerialName("album_name") val name: String,
    @SerialName("album_rating") val rating: Int,
    @SerialName("album_release_date") val releaseDate: String,

    @SerialName("artist_id") val artistId: Int,
    @SerialName("artist_name") val artistName: String,
    @SerialName("album_pline") val albumPline: String,
    @SerialName("album_copyright") val albumCopyright: String,
    @SerialName("album_label") val albumLabel: String,

    @SerialName("primary_genres") val genres: List<MusixGenre>,

    @SerialName("restricted") val isRestricted : Boolean,
    @SerialName("external_ids") val externalIdentities : MusixExternalIdentities,

    @SerialName("updated_time") val updatedTime: String
)

