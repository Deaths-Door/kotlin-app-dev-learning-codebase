package com.deathsdoor.chillback.common.ui.components.lazylists.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.deathsdoor.chillback.common.data.music.Album

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal expect fun LazyAlbumListVerticalItem(
    album : Album,
    useDefaultBehaviour : Boolean = true,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {},
)