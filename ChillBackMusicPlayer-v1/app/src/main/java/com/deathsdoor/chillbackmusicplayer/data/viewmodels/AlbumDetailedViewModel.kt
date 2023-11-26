package com.deathsdoor.chillbackmusicplayer.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Album

class AlbumDetailedViewModel: ViewModel() {
    var album = MutableLiveData<Album>()

}