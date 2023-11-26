package com.deathsdoor.chillbackmusicplayer.data.extensions

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
fun Modifier.onLongClick(onLongClick: () -> Unit): Modifier =
    this.then(Modifier.pointerInput(Unit){
        detectTapGestures(
            onLongPress = {
                onLongClick()
            }
        )
    })
