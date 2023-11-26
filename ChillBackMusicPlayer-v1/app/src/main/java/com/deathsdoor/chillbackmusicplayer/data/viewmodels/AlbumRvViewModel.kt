package com.deathsdoor.chillbackmusicplayer.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deathsdoor.chillbackmusicplayer.data.Constants
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Album

class AlbumRvViewModel :ViewModel() {
    val displayedAlbums = MutableLiveData<List<Album>>()
    val orientation = MutableLiveData<Constants.ORIENTATION>()
    var disableForwardNavigation = false
    var disableCheckAlbumDownload = false
}