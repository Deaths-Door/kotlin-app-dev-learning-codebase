package com.deathsdoor.chillbackmusicplayer.data.dataclasses.app

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.isValidPath
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.isValidURL
import kotlinx.parcelize.Parcelize
import java.io.File

//TODO add more fields like year
//TODO maybe add field to tell if internal file
@Entity
@Parcelize
//TODO store uris and use toString() instead
data class Song(
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val genre: String = "",
    val albumArtist: String = "",
    //TODO make the file path or make it the lyrics but idk how to treat this field
    //TODO maybe have got function below for it lets c what happens
    val lyricSource : String = "",
    //TODO make this the file path if its a local file internal or external and songURL
    val songSource:String ="",
    //TODO make this the imageSource as URL or search the song in storage to get its data
    val imageSource: String = "",
    @PrimaryKey(autoGenerate = false) val mediaID: String = ""3
) : Parcelable {

    fun toMediaIDSong(): MediaID = MediaID(this.mediaID)

    fun imageURI(): Uri {
        if(imageSource == "") return Uri.parse("")
        if(imageSource.isValidPath()) return Uri.fromFile(File(this.imageSource))
        else if(imageSource.isValidURL()) return Uri.parse(this.imageSource)
        throw IllegalArgumentException("Invalid imageSource its not File or URL")
    }
    fun songURI(): Uri {
        if(songSource.isValidPath()) return Uri.fromFile(File(this.songSource))
        else if(songSource.isValidURL()) return Uri.parse(this.songSource)
        throw IllegalArgumentException("Invalid songSource its not File or URL")
    }

    fun getLyrics(): String {
        if(lyricSource.isValidPath()) return File(lyricSource).readText().replace("\r\n", "\n")
        return lyricSource
    }

}