package com.deathsdoor.chillback.common.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.deathsdoor.chillback.common.data.music.ExtractMatchMusicFiles
import com.deathsdoor.chillback.common.ui.components.lazylists.LazySongList
import com.deathsdoor.chillback.common.ui.components.searchbar.SearchBar
import com.deathsdoor.chillback.common.ui.providers.GlobalViewModel

@Composable
internal actual fun LocalMusic() {
    val viewmodel = GlobalViewModel.current
    val songs by viewmodel.songsOnUserDevice.collectAsState()
    var query by remember { mutableStateOf("") }


    ExtractMatchMusicFiles()

    Column {
        SearchBar(
            initialQuery  = query,
            onQueryChange = {
                query = it
            }
        )

        LazySongList(
            mediaItems = songs,
            showSortingChips = true,
        )
    }
}