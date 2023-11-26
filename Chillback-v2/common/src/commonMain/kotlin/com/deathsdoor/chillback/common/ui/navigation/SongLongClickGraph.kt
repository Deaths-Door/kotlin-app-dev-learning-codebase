package com.deathsdoor.chillback.common.ui.navigation

import androidx.compose.runtime.Composable
import com.arkivanov.essenty.parcelable.Parcelize
import com.deathsdoor.astroplayer.core.dataclasses.MediaItem
import com.deathsdoor.chillback.common.ui.screens.editors.LyricEditor
import com.deathsdoor.chillback.common.ui.screens.editors.MetadataEditor
import com.deathsdoor.chillback.common.ui.screens.options.ExtraSongOptions

//TODO maybe pass mediaItem and local router wiht composition provider decide later cuz less args and more safer

@Parcelize
enum class SongLongClickGraph : Route {
    ExtraSongOptions {
        @Composable
        override fun content(vararg args: Any) = ExtraSongOptions(args[0] as MediaItem)
    },
    LyricEditor{
        @Composable
        override fun content(vararg args: Any) = LyricEditor(args[0] as MediaItem)

    },
    MetadataEditor{
        @Composable
        override fun content(vararg args: Any) = MetadataEditor(args[0] as MediaItem)
    }
}