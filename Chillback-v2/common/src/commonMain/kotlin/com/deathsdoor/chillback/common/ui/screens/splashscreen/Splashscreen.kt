package com.deathsdoor.chillback.common.ui.screens.splashscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow


@Composable
internal fun SplashScreen(onFinishContent : @Composable () -> Unit){
    var isAnimationFinished by remember { mutableStateOf(false) }
    LaunchedEffect(Unit){
        delay(1500)
        isAnimationFinished = true
    }
    if(isAnimationFinished) onFinishContent()
    else SplashScreenContent()
}