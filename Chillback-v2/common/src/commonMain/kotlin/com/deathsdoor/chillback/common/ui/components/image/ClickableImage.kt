package com.deathsdoor.chillback.common.ui.components.image

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ClickableImage(
    modifier: Modifier = Modifier,
    image: String,
    contentDescription : String? = null,
    enabled : Boolean = true,
    onClick : () -> Unit,
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        onClick = { onClick() },
        content = {
            AdvancedImage(image = image, contentDescription = contentDescription)
        }
    )
}