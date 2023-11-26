package com.deathsdoor.chillbackmusicplayer.data.music

import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import org.jaudiotagger.tag.images.Artwork
import java.io.File

class JAudioTagger(private val file: File) {
    constructor(path:String):this(File(path))

    private val audioFile: AudioFile get() = AudioFileIO.read(file)
    private val tag: Tag get() = audioFile.tagOrCreateAndSetDefault

    private fun saveChanges() = audioFile.commit()

    val title:String get() = tag.getFirst(FieldKey.TITLE)
    val artist: String get() = tag.getFirst(FieldKey.ARTIST)
    val album: String get() = tag.getFirst(FieldKey.ALBUM)
    val albumArtist: String get() = tag.getFirst(FieldKey.ALBUM_ARTIST)
    val albumArtWork: Artwork? get() = tag.artworkList[0]
    val year: String get() = tag.getFirst(FieldKey.YEAR)
    val genre: String get() = tag.getFirst(FieldKey.GENRE)

    fun changeTitle(title:String) {
        tag.setField(FieldKey.TITLE,title)
        saveChanges()
    }

    fun changeArtist(artist: String) {
        tag.setField(FieldKey.ARTIST, artist)
        saveChanges()
    }

    fun changeAlbum(album: String) {
        tag.setField(FieldKey.ALBUM, album)
        saveChanges()
    }

    fun changeAlbumArtist(artist: String) {
        tag.setField(FieldKey.ALBUM_ARTIST, artist)
        saveChanges()
    }

    fun changeYear(year: String) {
        tag.setField(FieldKey.YEAR, year)
        saveChanges()
    }

    fun changeGenre(genre: String) {
        tag.setField(FieldKey.GENRE, genre)
        saveChanges()
    }
}