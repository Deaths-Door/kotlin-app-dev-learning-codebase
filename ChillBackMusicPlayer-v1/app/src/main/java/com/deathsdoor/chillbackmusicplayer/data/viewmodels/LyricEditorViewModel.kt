package com.deathsdoor.chillbackmusicplayer.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.customviews.SongLyrics
import com.deathsdoor.chillbackmusicplayer.data.music.MusixSpiele

class LyricEditorViewModel: ViewModel() {
    val parsedFileContent = MutableLiveData<SongLyrics>()
    lateinit var musixSpiele: MusixSpiele
}