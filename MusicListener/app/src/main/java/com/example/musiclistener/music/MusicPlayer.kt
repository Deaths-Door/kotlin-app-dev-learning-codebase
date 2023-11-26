package com.example.musiclistener.music

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.musiclistener.Playlists
import com.example.musiclistener.Song
import com.example.musiclistener.music.MusicDownloading.localFileExists
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import java.io.File

import android.content.Context
import android.os.Handler
import com.example.musiclistener.CommonFunctions.canonicalFileDir
import com.example.musiclistener.Notification
import com.google.android.exoplayer2.ExoPlayer
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.roundToInt
/*
class MusicPlayerq(requireActivity: FragmentActivity){
    private val vm = ViewModelProvider(requireActivity)[MusicViewModel::class.java]
    private val fileDir = requireActivity.filesDir.canonicalFile
    //Remove current queue
    fun newQueue(queueItems:Playlists){
        vm.exoPlayer!!.removeMediaItems(0,vm.exoPlayer!!.mediaItemCount)
        addMediaItems(queueItems)
        vm.exoPlayer!!.play()
    }
    private fun addMediaItem(index:Int = vm.exoPlayer!!.mediaItemCount, song:Song){
        val metadata = MediaMetadata.Builder()
            .setTitle(song.title).setArtist(song.artist)
            .setGenre(song.genre).setArtworkUri(Uri.parse(song.imgURL))
            .setAlbumTitle(song.album)
            .build()
        val uri =
            if(localFileExists("${song.mediaID}.mp3",fileDir)) Uri.fromFile(File(fileDir,"${song.mediaID}.mp3"))
            else Uri.parse(song.songURL)
        val mediaItem = MediaItem.Builder().setMediaMetadata(metadata).setUri(uri).setMediaId(song.mediaID).build()
        vm.exoPlayer!!.addMediaItem(index,mediaItem)
    }
    private fun addMediaItems(queueItems: Playlists) = queueItems.songs.forEach{ addMediaItem(song = it) }
    //TODO pause song to
    //TODO continue playing current music
    fun previewSong(song:Song,context:Context){

        //Preview song
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        if(localFileExists("${song.mediaID}.mp3",fileDir))
            mediaPlayer.setDataSource(context,Uri.fromFile(File(fileDir,"${song.mediaID}.mp3")))
        else Uri.parse(song.songURL)
        mediaPlayer.prepare()
        mediaPlayer.seekTo((mediaPlayer.duration * 0.1).roundToInt())
        mediaPlayer.start()

        //delay for smtime
        Timer().schedule(10000) { mediaPlayer.release() }
    }
    fun playPlaylist(song: Song){ addMediaItem(vm.exoPlayer!!.currentMediaItemIndex + 1,song) ; vm.exoPlayer!!.next() }
}*/

object MusicPlayer{
    //Remove current queue
    fun ExoPlayer.newQueue(queueItems:Playlists,fileDir: File){
        removeMediaItems(0,this.mediaItemCount)
        addMediaItems(queueItems,fileDir)
        play()
        (View as Context).canonicalFileDir()
    }
     private fun ExoPlayer.addMediaItems(queueItems: Playlists, fileDir: File) = queueItems.songs.forEach{ addMediaItem(song = it, fileDir = fileDir) }
     private fun ExoPlayer.addMediaItem(index:Int = mediaItemCount, song:Song,fileDir:File){
        val metadata = MediaMetadata.Builder()
            .setTitle(song.title).setArtist(song.artist)
            .setGenre(song.genre).setArtworkUri(Uri.parse(song.imgURL))
            .setAlbumTitle(song.album)
            .build()
        val uri =
            if(localFileExists("${song.mediaID}.mp3",fileDir)) Uri.fromFile(File(fileDir,"${song.mediaID}.mp3"))
            else Uri.parse(song.songURL)
        val mediaItem = MediaItem.Builder().setMediaMetadata(metadata).setUri(uri).setMediaId(song.mediaID).build()
        addMediaItem(index,mediaItem)
    }
    //TODO pause song to
    //TODO continue playing current music
    fun previewSong(song:Song,context:Context,fileDir: File){

        //Preview song
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        if(localFileExists("${song.mediaID}.mp3",fileDir))
            mediaPlayer.setDataSource(context,Uri.fromFile(File(fileDir,"${song.mediaID}.mp3")))
        else Uri.parse(song.songURL)
        mediaPlayer.prepare()
        mediaPlayer.seekTo((mediaPlayer.duration * 0.1).roundToInt())
        mediaPlayer.start()

        //delay for smtime
        Timer().schedule(10000) { mediaPlayer.release() }
    }
    fun ExoPlayer.playPlaylist(song: Song, fileDir: File){ addMediaItem(currentMediaItemIndex + 1,song,fileDir); next() }
}

