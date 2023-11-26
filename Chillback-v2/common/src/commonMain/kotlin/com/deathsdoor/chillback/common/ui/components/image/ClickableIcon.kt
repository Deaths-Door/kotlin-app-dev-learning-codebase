package com.deathsdoor.chillback.common.ui.components.image

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ClickableIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription : String? = null,
    enabled : Boolean = true,
    onClick : () -> Unit,
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        content = {
            Icon(imageVector = imageVector, contentDescription = contentDescription)
        }
    )
}