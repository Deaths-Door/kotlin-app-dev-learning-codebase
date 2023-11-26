package com.deathsdoor.chillback.common.ui.modifiers

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@Stable
fun Modifier.fillFractionalSize(height : Float = 1f, width : Float = 1f): Modifier = composed {
    return@composed this@fillFractionalSize.fillMaxHeight(height).fillMaxWidth(width)
}