package com.deathsdoor.chillback.common.ui.components.lazylists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deathsdoor.astroplayer.core.dataclasses.MediaItem
import com.deathsdoor.chillback.common.data.music.Album
import com.deathsdoor.chillback.common.ui.components.lazylists.items.LazyAlbumListVerticalItem

@Composable
internal expect fun LazyAlbumList(
    modifier: Modifier = Modifier,
    albums: MutableList<Album>,
    useDefaultBehaviour: Boolean = true,
    showSortingChips: Boolean = false,
    emptyListMessage: @Composable () -> Unit = {
        //TODO update this msg correctly
        /*Column(
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
        }*/
    },
    nonDefaultBehaviourOnClick : (album : Album) -> Unit = {}
)