package com.deathsdoor.chillbackmusicplayer.data.dataclasses

data class Song(
    var metadata: Metadata = Metadata(),
    var songSource: DataSource? = null,
    var isLiked:Boolean = false
)