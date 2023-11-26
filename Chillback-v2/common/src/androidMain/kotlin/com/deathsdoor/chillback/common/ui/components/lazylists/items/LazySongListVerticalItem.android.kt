package com.deathsdoor.chillback.common.ui.components.lazylists.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deathsdoor.astroplayer.core.dataclasses.MediaItem
import com.deathsdoor.chillback.common.ui.components.text.MarqueeText

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal actual fun LazySongListVerticalItem(
    mediaItem: MediaItem,
    useDefaultBehaviour: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onLikeStatusChange: (Boolean) -> Unit
){
    Box(modifier = Modifier.combinedClickable(onClick = onClick, onLongClick = onLongClick)){
        Row {
            /*AdvancedImage(
                //modifier = Modifier.heightWidthIn(heightMin = 16.dp, widthMin = 16.dp),
                image = mediaItem.metadata.artwork.toString(),
                contentDescription = "${mediaItem.metadata.title} song image"
            )*/
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.75f)
            ) {
                MarqueeText(
                    text = mediaItem.metadata.title,
                    textStyle = MaterialTheme.typography.titleMedium
                )
                MarqueeText(
                    text = mediaItem.metadata.artist?.joinToString { "$it ," } ?: "No Given Artists",
                    textStyle = MaterialTheme.typography.bodySmall
                )
            }
        }

       // if(!useDefaultBehaviour) return@Box

        ////TODO add like btn
    }
}