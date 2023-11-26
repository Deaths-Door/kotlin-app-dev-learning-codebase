package com.deathsdoor.chillbackmusicplayer.test

import com.google.gson.annotations.SerializedName

data class MusixMatchResponse(

	@field:SerializedName("message")
	val message: Message
)

data class ArtistListItem(
	@field:SerializedName("artist")
	val artist: Artist
)

data class Artist(

	@field:SerializedName("artist_alias_list")
	val artistAliasList: List<Any>,

	@field:SerializedName("updated_time")
	val updatedTime: String,

	@field:SerializedName("artist_name")
	val artistName: String,

	@field:SerializedName("artist_rating")
	val artistRating: Int,

	@field:SerializedName("artist_id")
	val artistId: Int,

	@field:SerializedName("artist_mbid")
	val artistMbid: String
)

data class Body(

	@field:SerializedName("artist_list")
	val artistList: List<ArtistListItem>
)

data class Header(

	@field:SerializedName("status_code")
	val statusCode: Int,

	@field:SerializedName("execute_time")
	val executeTime: Any
)

data class Message(

	@field:SerializedName("header")
	val header: Header,

	@field:SerializedName("body")
	val body: Body
)
