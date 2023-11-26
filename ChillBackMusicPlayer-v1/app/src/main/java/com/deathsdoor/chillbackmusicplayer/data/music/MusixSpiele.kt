package com.deathsdoor.chillbackmusicplayer.data.music

import android.content.Context
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Song
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata

class MusixSpiele(context:Context) {
    val exoplayer :ExoPlayer = ExoPlayer.Builder(context)
        .setSeekBackIncrementMs(10000)
        .setSeekForwardIncrementMs(10000)
        .build()
        .also { it.prepare() }

    private fun List<Song>.addSongsToQueue() = this.forEach { addSongToQueue(it) }

    //TODO add image and song source into metadata builder
    private fun Song.addMediaItem(index: Int = exoplayer.mediaItemCount){
        val metadata = MediaMetadata.Builder()
            .setTitle(this.metadata.name)
            .setArtist(this.metadata.artist)
            .setGenre(this.metadata.genre)
            .setAlbumTitle(this.metadata.album)
            .setAlbumArtist(this.metadata.albumArtist)
            //.setArtworkUri(this.metadata.imageSource)
            .build()

        val mediaItem = MediaItem.Builder()
            .setMediaMetadata(metadata)
            .setMediaId(this.metadata.id)
            //.setUri(this.songSource?.type)
            .build()
        exoplayer.addMediaItem(index,mediaItem)
    }

    fun addSongToQueue(song: Song) = song.addMediaItem(exoplayer.mediaItemCount + 1)
    fun refreshQueue(queueItems: List<Song>){
        exoplayer.removeMediaItems(0,exoplayer.mediaItemCount)
        queueItems.addSongsToQueue()
        exoplayer.play()
    }
}