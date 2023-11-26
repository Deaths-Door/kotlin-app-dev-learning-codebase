package com.deathsdoor.chillback.common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.deathsdoor.chillback.common.ui.components.text.MarqueeText
import com.deathsdoor.chillback.common.ui.providers.GlobalErrorSnackbarState
import com.deathsdoor.chillback.common.ui.providers.GlobalInfoSnackbarState
import com.deathsdoor.chillback.common.ui.providers.GlobalSuccessSnackbarState

@Composable
internal fun InitializeSnackbars(){
    mapOf<SnackbarHostState,@Composable  (string : String) -> Unit>(
        GlobalErrorSnackbarState.current to { Error(it)},
        GlobalSuccessSnackbarState.current to { Success(it) },
        GlobalInfoSnackbarState.current to { Info(it) }
    ).forEach { (state,action) ->
        SnackbarHost(
            hostState = state,
            modifier = Modifier.padding(8.dp),
            snackbar = {
                action(it.visuals.message)
            }
        )
    }
}


@NonRestartableComposable
@Composable
internal fun Warning(message: String) = Toast(message,Icons.Default.Share,Color(0xFF8B8000))

@NonRestartableComposable
@Composable
internal fun Error(message: String) = Toast(message,Icons.Default.Share,Color.Red)

@NonRestartableComposable
@Composable
internal fun Success(message: String) = Toast(message,Icons.Default.Notifications,Color.Green)

@NonRestartableComposable
@Composable
internal fun Info(message: String) = Toast(message,Icons.Default.Info,Color.Blue)
@Composable
private fun Toast(message : String, imageVector: ImageVector, backgroundColor : Color) = Snackbar(
    shape = CircleShape,
    containerColor = backgroundColor,
    content = {
        Row {
            Image(imageVector = imageVector,contentDescription = null)
            MarqueeText(text = message)
        }
    }
)