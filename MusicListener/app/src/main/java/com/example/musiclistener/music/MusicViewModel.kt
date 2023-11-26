package com.example.musiclistener.music

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musiclistener.Artist
import com.example.musiclistener.Constants.FB_CURRENT_USER
import com.example.musiclistener.Constants.USE_USER_PLAYLIST
import com.example.musiclistener.Playlists
import com.example.musiclistener.SETTINGS
import com.example.musiclistener.Song
import com.google.android.exoplayer2.ExoPlayer

class MusicViewModel: ViewModel(){
    var exoPlayer: ExoPlayer? = null
    @Deprecated("Use new method idiot" , ReplaceWith("currentPlaylist"))
    var currentPlaylistData = MutableLiveData<Playlists>()
    //TODO please use this
    var showSongList = MutableLiveData<List<Song>>()
    var userPlaylist = MutableLiveData<List<Playlists>>()
    var currentPlaylist = MutableLiveData<Playlists>()
    var filteredSongs =  MutableLiveData<List<Song>>()
    var followedArtists = MutableLiveData<List<Artist>>()
    var currentArtist = MutableLiveData<Artist>()
    var localMusicData = MutableLiveData<List<Song>>()
    var moveToPlaylistView = true
    var currentDataToUse = USE_USER_PLAYLIST
    var songInfo : Song = Song()
    var showAddBtn = false
    val webPageURl = MutableLiveData<String>()
    var userSettings = hashMapOf(
        SETTINGS.CURRENT_USER_UUID.id to (FB_CURRENT_USER?.uid ?: SETTINGS.CURRENT_USER_UUID.default),
        SETTINGS.NORMALIZE_TRACK.id to SETTINGS.NORMALIZE_TRACK.default,
        SETTINGS.SKIP_SILENCE.id to SETTINGS.SKIP_SILENCE.default,
        SETTINGS.CROSS_FADING.id to SETTINGS.CROSS_FADING.default,
        SETTINGS.MOBILE_DATA_STREAMING_VIDEOS.id to SETTINGS.MOBILE_DATA_STREAMING_VIDEOS.default,
        SETTINGS.MOBILE_DATA_STREAMING_MUSIC.id to SETTINGS.MOBILE_DATA_STREAMING_MUSIC.default,
        SETTINGS.MOBILE_DATA_DOWNLOADING_MUSIC_FILES.id to SETTINGS.MOBILE_DATA_DOWNLOADING_MUSIC_FILES.default,
        SETTINGS.MOBILE_DATA_DOWNLOADING_VIDEO_FILES.id to SETTINGS.MOBILE_DATA_DOWNLOADING_VIDEO_FILES.default,
        SETTINGS.MOBILE_DATA_DOWNLOADING_LYRIC_FILES.id to SETTINGS.MOBILE_DATA_DOWNLOADING_LYRIC_FILES.default,
        SETTINGS.SHOW_LOCAL_MUSIC_FILES.id to SETTINGS.SHOW_LOCAL_MUSIC_FILES.default,
        SETTINGS.SPRACHE.id to SETTINGS.SPRACHE,
        SETTINGS.SPOTIFY_USERNAME.id to SETTINGS.SPOTIFY_USERNAME.default,
        SETTINGS.SPOTIFY_PASSWORD.id to SETTINGS.SPOTIFY_PASSWORD.default,
        SETTINGS.YOUTUBE_USERNAME.id to SETTINGS.YOUTUBE_USERNAME.default,
        SETTINGS.YOUTUBE_PASSWORD.id to SETTINGS.YOUTUBE_PASSWORD.default,
        SETTINGS.YOUTUBE_MUSIC_USERNAME.id to SETTINGS.YOUTUBE_MUSIC_USERNAME.default,
        SETTINGS.YOUTUBE_MUSIC_PASSWORD.id to SETTINGS.YOUTUBE_MUSIC_PASSWORD.default,
        SETTINGS.APPLE_MUSIC_USERNAME.id to SETTINGS.APPLE_MUSIC_USERNAME.default,
        SETTINGS.APPLE_MUSIC_PASSWORD.id to SETTINGS.APPLE_MUSIC_PASSWORD.default,
        SETTINGS.AMAZON_MUSIC_USERNAME.id to SETTINGS.AMAZON_MUSIC_USERNAME.default,
        SETTINGS.AMAZON_MUSIC_PASSWORD.id to SETTINGS.AMAZON_MUSIC_PASSWORD.default,
        SETTINGS.TIDAL_USERNAME.id to SETTINGS.TIDAL_USERNAME.default,
        SETTINGS.TIDAL_PASSWORD.id to SETTINGS.TIDAL_PASSWORD.default,
    )



    var playlistData = MutableLiveData<List<Playlists>>()
}