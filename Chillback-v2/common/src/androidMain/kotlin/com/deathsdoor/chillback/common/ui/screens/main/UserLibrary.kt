package com.deathsdoor.chillback.common.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deathsdoor.chillback.common.data.music.Album
import com.deathsdoor.chillback.common.data.settings.Settings
import com.deathsdoor.chillback.common.ui.components.image.ClickableIcon
import com.deathsdoor.chillback.common.ui.components.image.ClickableImage
import com.deathsdoor.chillback.common.ui.components.lazylists.LazyAlbumList
import com.deathsdoor.chillback.common.ui.providers.GlobalViewModel
import com.deathsdoor.chillback.common.ui.screens.options.AddSongToAlbum
import com.deathsdoor.chillback.common.ui.screens.options.CreateNewPlaylist
import com.deathsdoor.chillback.common.ui.screens.settings.Screen
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import eu.wewox.modalsheet.ExperimentalSheetApi
import eu.wewox.modalsheet.ModalSheet

@OptIn(ExperimentalSheetApi::class)
@Composable
internal actual fun UserLibrary() {
    val albums by GlobalViewModel.current.userPlaylistsOnCloud.collectAsState()
  //  var isCreateNewPlaylistVisible by remember { mutableStateOf(false) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var isCreateNewPlaylist by remember { mutableStateOf(false) }

    Column {
        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            ClickableImage(
                modifier = Modifier
                    .size(48.dp)
                    .padding(start = 8.dp,end = 8.dp),
                image = Firebase.auth.currentUser!!.android.photoUrl.toString(),
                contentDescription = "Your Profile Image",
                onClick = {
                    isBottomSheetVisible = true
                    isCreateNewPlaylist = false
                }
            )

            Text(
                text = "Your Library",
                style = MaterialTheme.typography.headlineMedium,
            )

            Spacer(modifier = Modifier.weight(1f))

            ClickableIcon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "Search Button",
                onClick = {
                    //TODO handle this
                }
            )

            ClickableIcon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Default.Add,
                contentDescription = "Create New Playlist Button",
                onClick = {
                    isBottomSheetVisible = true
                    isCreateNewPlaylist = true
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyAlbumList(albums = albums)
    }

    ModalSheet(
        visible = isBottomSheetVisible,
        onVisibleChange = {
            isBottomSheetVisible = it

          //  if(isCreateNewPlaylist) isCreateNewPlaylist = false
        },
        content = {
            if(isCreateNewPlaylist){
                CreateNewPlaylist(onFinish = {
                    AddSongToAlbum(album = Album(name = it))
                })
            }
            else Settings.Screen()
        }
    )
}