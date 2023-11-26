package com.deathsdoor.chillback.common.data.viewmodel

import com.deathsdoor.astroplayer.core.dataclasses.MediaItem
import com.deathsdoor.chillback.common.data.music.Album
import kotlinx.coroutines.flow.MutableStateFlow
import com.hoc081098.kmp.viewmodel.ViewModel as ExternalViewModel
internal class GlobalViewModel : ExternalViewModel() {
    val songsOnUserDevice = MutableStateFlow(mutableListOf<MediaItem>())
    var isSongsOnUserDeviceDetectionRunning = false

    val userPlaylistsOnCloud = MutableStateFlow(mutableListOf<Album>())

    @Deprecated("TO WORK ROUND THE DECOMPOSE PARCEABLE ISSUE FOR ALBUM CLASS SO REMOVE THIS ONCE IT IS SOLVED")
    var seletedAlbum : Album? = null
}