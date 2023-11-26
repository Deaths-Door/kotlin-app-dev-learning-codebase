package com.example.musiclistener.music

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.musiclistener.CONNECTION
import com.example.musiclistener.CommonFunctions.canonicalFileDir
import com.example.musiclistener.Constants.FB_STORAGE
import com.example.musiclistener.Notification
import com.example.musiclistener.Playlists
import com.example.musiclistener.SETTINGS
import java.io.File

object MusicDownloading {
    fun localFileExists(name: String, fileDir: File): Boolean = File(File(fileDir, ""),name).exists()
    private fun Context.checkForInternetType(): Int {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return when{
            network == null -> CONNECTION.NONE.id
            // Indicates this network uses constants Wi-Fi transport or WiFi has network connectivity
            network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> CONNECTION.WLAN.id
            // Indicates this network uses constants Cellular transport or Cellular has network connectivity
            network.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> CONNECTION.MOBILE.id
            // else return false
            else -> CONNECTION.NONE.id
        }
    }
    fun Context.downloadUserSongs(playlist:List<Playlists>, context:Context,vm: MusicViewModel) {
        Notification(this).createProgressNotification()
        playlist.forEach { itPlaylist -> itPlaylist.songsIDs.forEach { itSongIDS -> context.downloadFiles(itSongIDS,vm) } }
        Notification(this).cancelProgressNotification()
    }
    private fun Context.downloadFiles(songID: String, vm: MusicViewModel){
        downloadMP3(songID,vm)
        downloadLRC(songID,vm)
        if(vm.userSettings[SETTINGS.DOWNLOAD_VIDEO_FILES.id] == true) downloadMP4(songID,vm)
    }
    private fun Context.downloadMP3(songID: String, vm: MusicViewModel){
        val fileDir = this.canonicalFileDir()

        //download nur wenn files nicht existeriert
        if(localFileExists("$songID.mp3",fileDir)) return

        val connection = checkForInternetType()
        if(connection == CONNECTION.NONE.id || (connection == CONNECTION.MOBILE.id && vm.userSettings[SETTINGS.MOBILE_DATA_DOWNLOADING_MUSIC_FILES.id] == false)) return

        val islandRefSong = FB_STORAGE.child("$songID/music.mp3")
        val rootPath = File(fileDir,"")
        if(!rootPath.exists()) rootPath.mkdirs()
        val localFile = File(rootPath,"$songID.mp3")
        islandRefSong.getFile(localFile)
            .addOnProgressListener {}
            .addOnFailureListener{ downloadMP3(songID, vm)}
    }
    private fun Context.downloadLRC(songID: String, vm: MusicViewModel) {
        val fileDir = this.canonicalFileDir()

        //download nur wenn files nicht existeriert
        if(localFileExists("$songID.lrc",fileDir)) return

        val connection = checkForInternetType()
        if(connection == CONNECTION.NONE.id  || (connection == CONNECTION.MOBILE.id && vm.userSettings[SETTINGS.MOBILE_DATA_DOWNLOADING_LYRIC_FILES.id] == false)) return

        val islandRefSong = FB_STORAGE.child("$songID/lyrics.lrc")
        val rootPath = File(fileDir, "")
        if (!rootPath.exists()) rootPath.mkdirs()
        val localFile = File(rootPath, "$songID.lrc")
        islandRefSong.getFile(localFile)
            .addOnProgressListener { }
            .addOnFailureListener { downloadLRC(songID, vm) }
    }
    private fun Context.downloadMP4(songID: String, vm: MusicViewModel) {
        val fileDir = this.canonicalFileDir()

        //download nur wenn files nicht existeriert
        if(localFileExists("$songID.lrc",fileDir)) return

        val connection = checkForInternetType()
        if(connection == CONNECTION.NONE.id  || (connection == CONNECTION.MOBILE.id && vm.userSettings[SETTINGS.MOBILE_DATA_DOWNLOADING_VIDEO_FILES.id] == false)) return

        val islandRefSong = FB_STORAGE.child("$songID/video.mp4")
        val rootPath = File(fileDir, "")
        if (!rootPath.exists()) rootPath.mkdirs()
        val localFile = File(rootPath, "$songID.mp4")
        islandRefSong.getFile(localFile)
            .addOnProgressListener {}
            .addOnFailureListener { downloadLRC(songID, vm) }
    }
}