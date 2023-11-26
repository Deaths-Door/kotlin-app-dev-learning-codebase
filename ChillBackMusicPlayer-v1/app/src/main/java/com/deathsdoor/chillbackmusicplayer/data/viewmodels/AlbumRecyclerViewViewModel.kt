package com.deathsdoor.chillbackmusicplayer.data.viewmodels

import androidx.lifecycle.ViewModel
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Album
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Orientation
import kotlinx.coroutines.flow.MutableStateFlow

@Deprecated("maybe use parms instead")
class AlbumRecyclerViewViewModel:ViewModel() {
    val displayedAlbums = MutableStateFlow(emptyList<Album>())
    val orientation = MutableStateFlow(Orientation.Vertical)
}