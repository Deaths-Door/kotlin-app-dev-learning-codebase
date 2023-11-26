package com.deathsdoor.chillbackmusicplayer.data.viewmodels

import androidx.lifecycle.ViewModel
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Song
import com.deathsdoor.chillbackmusicplayer.data.music.MusixSpiele
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel:ViewModel() {
    lateinit var musixSpiele: MusixSpiele
    val songsOnUserExternalStorage = MutableStateFlow<List<Song>>(emptyList())
}