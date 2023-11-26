package com.deathsdoor.chillbackmusicplayer.ui.components

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.findNavController
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.music.MusixSpiele
import com.google.android.exoplayer2.ui.PlayerControlView

@Composable
fun ExoPlayerMusixSpiele(modifier:Modifier = Modifier,musixSpiele: MusixSpiele){
    AndroidView(modifier = modifier, factory = { context ->
        LayoutInflater.from(context).inflate(R.layout.root_exoplayer_musixspiele,null).apply {
            findViewById<PlayerControlView>(R.id.exoplayer).player = musixSpiele.exoplayer
        }
    })
}