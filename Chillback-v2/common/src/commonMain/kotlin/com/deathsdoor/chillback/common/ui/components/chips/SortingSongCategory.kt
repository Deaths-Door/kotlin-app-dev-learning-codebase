package com.deathsdoor.chillback.common.ui.components.chips

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.deathsdoor.astroplayer.core.dataclasses.MediaItem

//TODO use string resources instead
internal enum class SortingCategory{
    SongName {

        override fun sort(songs: MutableList<MediaItem>) : MutableList<MediaItem> = songs.sortedBy { it.metadata.title }.toMutableList()

        @Composable
        override fun displayName() : String = "By Name"

        override val icon: ImageVector by lazy { Icons.Default.Star }
    },
    SongDuration {
        
        override fun sort(songs: MutableList<MediaItem>): MutableList<MediaItem> = songs.sortedBy { it.metadata.trackLength }.toMutableList()

        @Composable 
        override fun displayName() : String = "By Duration"

        override val icon: ImageVector by lazy { Icons.Default.Star }

    },
    SongLikedStatus {

        override fun sort(songs: MutableList<MediaItem>): MutableList<MediaItem> = songs.sortedBy { it.metadata.isLiked }.toMutableList()

        @Composable 
        override fun displayName() : String = "By Like Status"

        override val icon: ImageVector by lazy { Icons.Default.Star }
    };

    abstract fun sort(songs : MutableList<MediaItem>): MutableList<MediaItem>

    @Composable
    abstract fun displayName() : String

    abstract val icon : ImageVector
}