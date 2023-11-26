package com.deathsdoor.chillback.common.ui.screens.editors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deathsdoor.astroplayer.core.dataclasses.MediaItem
import com.deathsdoor.chillback.common.ui.components.image.AdvancedImage
import com.deathsdoor.chillback.common.ui.modifiers.fillFractionalSize
import com.dokar.chiptextfield.Chip
import com.dokar.chiptextfield.ChipTextField
import com.dokar.chiptextfield.rememberChipTextFieldState

//TODO maybe add undo feature
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal actual fun MetadataEditor(mediaItem: MediaItem){
    val name = remember { mutableStateOf(mediaItem.metadata.title ) }
    val album = remember { mutableStateOf(mediaItem.metadata.albumTitle ?: "No Given Title")}

    Box {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
            Button()
        }
        AdvancedImage(
            modifier = Modifier.fillMaxHeight(0.4f),
            image = mediaItem.metadata.artwork.toString(),
            contentDescription = null
        )
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillFractionalSize(height = 0.7f)
        ){
            mapOf(
                "Name" to name,
                "Album Title" to album
            ).forEach { (text,variable) ->
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = variable.value,
                    onValueChange = {
                        variable.value = it
                    },
                    label = { Text(text) }
                )
            }
        }
    }
}