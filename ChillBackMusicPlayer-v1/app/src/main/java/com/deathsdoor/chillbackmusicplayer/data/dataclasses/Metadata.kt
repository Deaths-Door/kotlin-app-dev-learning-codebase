package com.deathsdoor.chillbackmusicplayer.data.dataclasses

//TODO make Dataouscr in this nonnullable
data class Metadata(
    val id: String = "",
    var name: String = "",
    var artist: String = "",
    var album: String = "",
    var albumArtist: String = "",
    var genre: String = "",
    var year: Int = 0,
    var duration: Int = 0,
    var trackNumber: Int = 0,
    var discNumber: Int = 0,
    var bpm: Int = 0,
    //TODO Could make it enum
    var mood: String = "",
    //TODO Could make it enum
    var language: String = "",
    var lyricSource: DataSource? = null,
    var imageSource: DataSource? = null,
)