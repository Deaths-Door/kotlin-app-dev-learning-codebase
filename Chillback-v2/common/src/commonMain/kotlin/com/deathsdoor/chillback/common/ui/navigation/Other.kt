package com.deathsdoor.chillback.common.ui.navigation

import androidx.compose.runtime.Composable
import com.arkivanov.essenty.parcelable.Parcelize
import com.deathsdoor.chillback.common.ui.providers.GlobalViewModel
import com.deathsdoor.chillback.common.ui.screens.other.DisplayAlbumDetails


//TODO raise issue about passing arguemnts on https://github.com/xxfast/Decompose-Router/tree/main that album doesnt support @Parcelize and hence need to go down the full tree instead
/*
@Parcelize
sealed class Other : Route {
    class DisplayAlbumDetails(val album : Album) : Other() {
        @Composable
        override fun content(vararg args: Any) = ComposableDisplayAlbumDetails(album)
    }
}
*/
@Parcelize
enum class Other : Route {
    //TODO handle this
    DisplayAlbumDetails {
        @Composable
        override fun content(vararg args: Any) = DisplayAlbumDetails(GlobalViewModel.current.seletedAlbum!!)
    }
}