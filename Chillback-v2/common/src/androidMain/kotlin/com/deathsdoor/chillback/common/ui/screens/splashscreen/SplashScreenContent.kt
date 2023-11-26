package com.deathsdoor.chillback.common.ui.screens.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.deathsdoor.chillback.common.resources.image.AppLogo
import com.deathsdoor.chillback.common.ui.modifiers.fillFractionalSize
import dev.icerock.moko.resources.compose.stringResource
import com.deathsdoor.chillback.common.resources.MR as resources

@Composable
internal actual fun SplashScreenContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.fillFractionalSize(0.2f, 0.3f),
            imageVector = Icons.AppLogo,
            contentDescription = null
        )
        Text(
            text = "Chillback",
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = stringResource(resource = resources.strings.app_slogan),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}