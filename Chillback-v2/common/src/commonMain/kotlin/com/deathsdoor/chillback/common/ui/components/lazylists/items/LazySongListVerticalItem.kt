package com.deathsdoor.chillback.common.ui.components.lazylists.items

import androidx.compose.runtime.Composable
import com.deathsdoor.astroplayer.core.dataclasses.MediaItem

@Composable
internal expect fun LazySongListVerticalItem(
    mediaItem: MediaItem,
    useDefaultBehaviour: Boolean = true,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {},
    onLikeStatusChange : (Boolean) -> Unit  = {}
)