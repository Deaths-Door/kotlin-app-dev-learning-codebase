package com.example.musiclistener

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class Song(
    val mediaID: String = "",
    val songURL: String = "",
    val imgURL: String = "",
    val videoURL: String = "",
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val genre: String = "",
    val releaseDay: String = "",
    val albumArtist: String = "",
)

@Entity
data class LocalSong(
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val genre: String = "",
    val albumArtist: String = "",
    @PrimaryKey(autoGenerate = false) val mediaID: String = ""
){
    fun convertToSongType(): Song {
        val mediaID = this.mediaID
        return Song(
            mediaID = this.mediaID,
            title = this.title,
            artist = this.artist,
            album = this.album,
            albumArtist = this.albumArtist,
            genre = this.genre
        )
    }
}

@Entity
data class Playlists(
    var name:String= "",
    var mainArtists: String = "",
    var songs:ArrayList<Song> = arrayListOf(),
    var songsIDs:ArrayList<String> = arrayListOf(),
    var pinned: Boolean = false,
    var hidden: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id:Long = 0,
    @Ignore var img: Bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888)
) {
    fun addSong(songData: Song):Playlists {
        this.songsIDs.add(songData.mediaID)
        this.songs.add(songData)
        return this
    }
    fun removeSong(songData:Song): Playlists {
        this.songsIDs.remove(songData.mediaID)
        this.songs.remove(songData)
        return this
    }
}

@Entity
data class Artist(
    val name:String= "",
    val description:String="",
    val profilePic:String= "",
    val aboutImgUrl:String="",
    val songsIDs: ArrayList<String> = arrayListOf(),
    var songs:ArrayList<Song> = arrayListOf(),
    var following: Boolean = false,
    val sozailMedien: ArrayList<String> = arrayListOf(),
    @PrimaryKey(autoGenerate = false) val artistID: String = ""
)