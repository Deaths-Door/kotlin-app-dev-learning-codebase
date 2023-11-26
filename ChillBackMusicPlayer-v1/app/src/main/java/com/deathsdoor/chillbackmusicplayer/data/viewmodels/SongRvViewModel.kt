package com.deathsdoor.chillbackmusicplayer.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deathsdoor.chillbackmusicplayer.data.Constants
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song

class SongRvViewModel : ViewModel() {
    val displayedSongs = MutableLiveData<List<Song>>()
    val orientation = MutableLiveData<Constants.ORIENTATION>()
    var disableForwardNavigation :Boolean = false

}