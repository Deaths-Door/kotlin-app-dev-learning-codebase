package com.deathsdoor.chillback.common.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.essenty.parcelable.Parcelize
import com.deathsdoor.chillback.common.ui.screens.main.EventMaps
import com.deathsdoor.chillback.common.ui.screens.main.ExploreNewContent
import com.deathsdoor.chillback.common.ui.screens.main.LocalMusic
import com.deathsdoor.chillback.common.ui.screens.main.UserLibrary

@Parcelize
internal enum class RootGraph : Route {
    UserLibrary {
        
        override val image: ImageVector = Icons.Default.Search

        @Composable
        override fun content(vararg args: Any) = UserLibrary()
    },
    ExploreNewContent {

        override val image : ImageVector = Icons.Default.Search


        @Composable
        override fun content(vararg args: Any) = ExploreNewContent()
    },
    EventMaps {

        override val image : ImageVector = Icons.Default.Search

        @Composable
        override fun content(vararg args: Any) = EventMaps()
    },
    LocalMusic {

        override val image : ImageVector = Icons.Default.Search

        @Composable
        override fun content(vararg args: Any) = LocalMusic()
    }
}