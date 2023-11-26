package com.deathsdoor.musicmatch.dataclasses

import kotlinx.serialization.*

@Serializable
data class MusixArtist(
    @SerialName("artist_id") val id: Int,
    @SerialName("artist_name") val name: String,
    @SerialName("artist_rating") val rating: Int,
    @SerialName("artist_comment") val comment: String,
    @SerialName("artist_twitter_url") val twitterURL:String,
    @SerialName("artist_country") val country:String,

    @SerialName("artist_name_translation_list")  val ubersetztedNames:List<MusixTranslation>,

    @SerialName("artist_credits") val credits: List<MusixArtist> = emptyList(),
    @SerialName("artist_alias_list") val alternativname : List<String>,

    @SerialName("restricted") val isRestricted: Boolean,

    @SerialName("updated_time") val updatedTime: String,
    @SerialName("begin_date_year") val careerBeginYear: String,
    @SerialName("begin_date") val careerBeginDater: String,
    @SerialName("end_date_year") val careerEndYear: String,
    @SerialName("end_date") val careerEndDater: String,
)