package com.deathsdoor.chillbackmusicplayer.ui.components.albumrecyclerview

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Orientation
import com.deathsdoor.chillbackmusicplayer.data.extensions.onLongClick
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.AlbumRecyclerViewViewModel


//TODO add all options to the recycler view (data: Album, isPinned: Boolean = false, isDownloaded: Boolean = false, onClick: () -> Unit)
@Composable
fun AlbumRecyclerView(viewmodel:AlbumRecyclerViewViewModel){
    val displayedAlbums by viewmodel.displayedAlbums.collectAsState()
    val orientation by viewmodel.orientation.collectAsState()

    val _modifier = Modifier.fillMaxSize()

    if(orientation == Orientation.Horizontal){
        LazyRow(_modifier){
            items(displayedAlbums){
                AlbumRecyclerViewItemHorizontal(data = it) {/*TODO do something*/ }
            }
        }
        return
    }
    LazyColumn(_modifier){
        if(orientation == Orientation.Vertical) items(displayedAlbums) { AlbumRecyclerViewItemVertical(data = it) {/*TODO do something*/ } }
        else items(displayedAlbums) { AlbumRecyclerViewItemGrid(data = it) {/*TODO do something*/ } }
    }
}