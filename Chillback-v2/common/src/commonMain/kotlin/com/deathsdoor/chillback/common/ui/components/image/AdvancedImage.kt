package com.deathsdoor.chillback.common.ui.components.image

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

//TODO make this a modifier
@Deprecated("Replace this with https://github.com/Kamel-Media/Kamel")
@Composable
internal expect fun AdvancedImage(
    //TODO make this a modifier
    modifier : Modifier = Modifier.size(32.dp),
    image: String?,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Fit
)