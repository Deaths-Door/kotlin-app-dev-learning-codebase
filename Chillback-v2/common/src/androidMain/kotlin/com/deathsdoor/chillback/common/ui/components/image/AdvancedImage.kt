package com.deathsdoor.chillback.common.ui.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
@Deprecated("Replace this with https://github.com/Kamel-Media/Kamel")
internal actual fun AdvancedImage(
    modifier: Modifier,
    image: String?,
    contentDescription: String?,
    contentScale: ContentScale
) {
    AsyncImage(
        modifier = modifier,
        model = image,
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}