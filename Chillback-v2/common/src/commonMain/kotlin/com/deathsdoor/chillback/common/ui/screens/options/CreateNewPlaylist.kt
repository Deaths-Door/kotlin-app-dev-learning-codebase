package com.deathsdoor.chillback.common.ui.screens.options

import androidx.compose.runtime.Composable
import com.deathsdoor.chillback.common.data.music.Album

@Composable
internal expect fun CreateNewPlaylist(onFinish :@Composable (title : String) -> Unit)