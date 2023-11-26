package com.deathsdoor.chillback.common.ui.components.lazylists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deathsdoor.astroplayer.core.dataclasses.MediaItem


@Composable
internal expect fun LazySongList(
    modifier: Modifier = Modifier,
    mediaItems : MutableList<MediaItem>,
    showSortingChips : Boolean = false,
    useDefaultBehaviour : Boolean = true,
    emptyListMessage: @Composable () -> Unit = {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "You don't have any songs in the playlist yet.\n Start by adding your first song.",
                style = MaterialTheme.typography.labelSmall,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                },
                content = {
                    Text(text = "Add your first song")
                }
            )
        }
    },
    nonDefaultBehaviourOnClick : (mediaItem : MediaItem) -> Unit = {}
)