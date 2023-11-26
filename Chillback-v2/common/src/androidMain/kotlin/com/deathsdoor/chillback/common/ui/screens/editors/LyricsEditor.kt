package com.deathsdoor.chillback.common.ui.screens.editors

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.deathsdoor.astroplayer.core.dataclasses.Lyric
import com.deathsdoor.astroplayer.core.dataclasses.MediaItem
import com.deathsdoor.chillback.common.data.secrets.ApiKeys
import com.deathsdoor.chillback.common.ui.components.image.ClickableIcon
import com.deathsdoor.chillback.common.ui.components.text.MarqueeText
import com.deathsdoor.chillback.common.ui.components.timepicker.TimePicker
import com.deathsdoor.musicmatch.MusixAbgleich
import kotlinx.coroutines.launch
import java.io.File
@Composable
internal actual fun LyricInput(mediaItem: MediaItem,onReceived : () -> Unit) {
    Column {
        Row {
            Text(text = "Search Lyrics",)

            Spacer(modifier = Modifier.weight(1f))

          //  val musixMatch : MusixAbgleich by lazy { MusixAbgleich(ApiKeys.musicmatch) }
           // val coroutineScope = rememberCoroutineScope()

            TextButton(
                onClick = {

                },
                content = { Text(text = "AutoSearch") }
            )


            val chooseFileLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
            ) { uri: Uri? ->
                onReceived()
                mediaItem.metadata.lyrics = Lyric.parseInput(
                    File(uri?.path).useLines {
                        it.joinToString(separator = "\n")
                    }
                )
            }

            //File
            ClickableIcon(
                imageVector = Icons.Default.Info,
                onClick = { chooseFileLauncher.launch("text/*") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        var input by remember { mutableStateOf("") }

        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End){
            Button(
                onClick = {
                    mediaItem.metadata.lyrics = Lyric.parseInput(input)
                    onReceived()
                },
                content = {
                    Text("Next..")
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = input,
            onValueChange = {
                input = it
            }
        )
    }
}

@Composable
internal actual fun LyricManipulator(mediaItem : MediaItem) {
    Box(modifier = Modifier.size(75.dp).background(Color.Red))
    LazyColumn {
        items(mediaItem.metadata.lyrics!!.lines){
            Row {
                TimePicker()
                MarqueeText(text = it.text)
            }
        }
    }
}