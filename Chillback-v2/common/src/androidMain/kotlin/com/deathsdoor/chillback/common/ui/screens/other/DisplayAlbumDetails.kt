package com.deathsdoor.chillback.common.ui.screens.other

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deathsdoor.chillback.common.data.music.Album
import com.deathsdoor.chillback.common.ui.components.image.AdvancedImage
import com.deathsdoor.chillback.common.ui.components.lazylists.LazySongList
import com.deathsdoor.chillback.common.ui.components.text.MarqueeText

@Composable
internal actual fun DisplayAlbumDetails(album: Album){
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxHeight(0.3f)) {
            AdvancedImage(
                modifier = Modifier.fillMaxSize(),
                image = album.image,
                contentDescription = null
            )

            MarqueeText(
                modifier = Modifier.align(BottomStart).padding(start = 8.dp,bottom = 8.dp),
                text = album.name,
                textStyle = MaterialTheme.typography.titleLarge
            )
        }

        LazySongList(mediaItems = album.songs)
    }
}