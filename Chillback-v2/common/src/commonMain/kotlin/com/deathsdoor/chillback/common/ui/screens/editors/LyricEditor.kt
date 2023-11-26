package com.deathsdoor.chillback.common.ui.screens.editors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.deathsdoor.astroplayer.core.dataclasses.MediaItem

@Composable
internal fun LyricEditor(mediaItem: MediaItem) {
    var isInputShown by remember { mutableStateOf(mediaItem.metadata.lyrics == null || mediaItem.metadata.lyrics?.lines?.isEmpty() == true) }
    if(isInputShown){
        LyricInput(mediaItem = mediaItem, onReceived = {
            isInputShown = false
        })
        return
    }
    LyricManipulator(mediaItem)
}

@Composable
internal expect fun LyricManipulator(mediaItem: MediaItem)

@Composable
internal expect fun LyricInput(mediaItem: MediaItem,onReceived : () -> Unit)