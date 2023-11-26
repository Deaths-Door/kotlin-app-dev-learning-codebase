package com.deathsdoor.chillbackmusicplayer.ui.splashscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

//TODO add effect of up down(by using bottomsheet)
@Composable
fun SplashScreen(shownDuration : Long = 1000,content : @Composable () -> Unit){
    val splashScreenDismissed = remember { mutableStateOf(false)}

    LaunchedEffect(Unit){
        delay(shownDuration)
        splashScreenDismissed.value = true
    }

    if(splashScreenDismissed.value) return
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primary.copy(1f))) { content() }
}