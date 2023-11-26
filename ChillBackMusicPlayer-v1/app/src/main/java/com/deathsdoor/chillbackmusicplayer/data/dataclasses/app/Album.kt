package com.deathsdoor.chillbackmusicplayer.data.dataclasses.app

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Album(
    var title: String = "",
    var albumArtist: String = "",
    var pinned: Boolean = false,
    var songs: ArrayList<Song> = arrayListOf(),
    var songsIDs: ArrayList<String> = arrayListOf(),
    val imgURL: String = "",
    @PrimaryKey(autoGenerate = false) val id: String = "",
) : Parcelable {
    fun addSong(it: Song): Album {
        this.songsIDs.add(it.mediaID)
        this.songs.add(it)
        return this
    }
    fun addSongs(array: ArrayList<Song>?): Album {
        array?.forEach { this.addSong(it) }
        return this
    }
}