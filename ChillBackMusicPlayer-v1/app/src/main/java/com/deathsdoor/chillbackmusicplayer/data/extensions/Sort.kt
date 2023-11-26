package com.deathsdoor.chillbackmusicplayer.data.extensions

import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Album
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song

object Sort {
    inline fun List<Song>.sort(by:(it:Song) -> List<String>): List<Album> {
        val map = HashMap<String, Album>()
        this.map {
            by(it).map { artist -> artist.trim() }.map { song->
                if (!map.containsKey(song)) map[song] = Album(title = song)
                map[song]!!.addSong(it)
            }
        }
        return map.values.toList().sortByTitle()
    }
    fun List<Album>.sortByTitle(): List<Album> = this.sortedBy { it.title }
}