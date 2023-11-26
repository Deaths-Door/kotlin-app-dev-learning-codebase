package com.deathsdoor.chillback.common.ui.screens.options

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.deathsdoor.chillback.common.data.firebase.insertToAppropriateStorage
import com.deathsdoor.chillback.common.data.music.Album
import com.deathsdoor.chillback.common.ui.components.lazylists.LazySongList
import com.deathsdoor.chillback.common.ui.providers.GlobalCoroutineScope
import com.deathsdoor.chillback.common.ui.providers.GlobalViewModel
import dev.gitlive.firebase.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal actual fun AddSongToAlbum(album : Album) {
    val globalCoroutineScope = GlobalCoroutineScope.current
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Button (
                onClick = {
                    //TODO add check that added songs isnt null so empty
                    globalCoroutineScope.launch {
                        Firebase.insertToAppropriateStorage(album)
                    }
                },
                content = {
                    Text("Finish")
                }
            )
        }

        var selectedChip by remember { mutableStateOf(0) }

        val data = listOf(
            Triple("Both", Icons.Default.Add, GlobalViewModel.current.songsOnUserDevice) ,
            Triple("Online", Icons.Default.Add,GlobalViewModel.current.songsOnUserDevice),
            Triple("Local", Icons.Default.Add,GlobalViewModel.current.songsOnUserDevice)
        )

        Row {
            data.forEachIndexed { index, it ->
                FilterChip(
                    selected = selectedChip == index,
                    onClick = {
                        if(it.first != data[2].first) selectedChip = index
                        //TODO request the permission for it
                        else {}
                    },
                    leadingIcon = {
                        Image(imageVector = if(it.first != data[2].first) it.second else Icons.Default.Lock,contentDescription = null)
                    },
                    label = {
                        Text(text = it.first)
                    }
                )
            }
        }

        LazySongList(
            modifier = Modifier.fillMaxHeight(),
            mediaItems = data[selectedChip].third.collectAsState().value,
            useDefaultBehaviour = false,
            nonDefaultBehaviourOnClick = {
                album.songs.add(it)
            }
        )
    }
}