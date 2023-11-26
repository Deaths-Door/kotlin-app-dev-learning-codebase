package com.deathsdoor.chillback.common.ui.components.lazylists.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.deathsdoor.chillback.common.data.music.Album
import com.deathsdoor.chillback.common.ui.components.image.AdvancedImage
import com.deathsdoor.chillback.common.ui.components.text.MarqueeText

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal actual fun LazyAlbumListVerticalItem(
    album : Album,
    useDefaultBehaviour : Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
){
    Box(modifier = Modifier.combinedClickable(onClick = onClick, onLongClick = onLongClick)){
        Row(verticalAlignment = Alignment.CenterVertically) {
            AdvancedImage(
                //TODO this causes error
                //    modifier = Modifier.size(48.dp),
                image = album.image,
                contentDescription = null
            )

            Column {
                MarqueeText(
                    modifier = Modifier,
                    text = album.name,
                    textStyle = MaterialTheme.typography.titleLarge
                )

                //TODO add isPinned icons
                Icon(
                    imageVector = if(album.isPinned) Icons.Default.Edit else Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                )

                //TODO add smth more here
            }
        }
    }

}