package com.deathsdoor.chillback.common.ui.components.mediaplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deathsdoor.astroplayer.ui.buttons.LikeButton
import com.deathsdoor.astroplayer.ui.buttons.NextMediaItemButton
import com.deathsdoor.astroplayer.ui.buttons.PlayPauseButton
import com.deathsdoor.astroplayer.ui.buttons.PreviousMediaItemButton
import com.deathsdoor.astroplayer.ui.images.TrackArtwork
import com.deathsdoor.astroplayer.ui.sliders.DurationSlider
import com.deathsdoor.astroplayer.ui.text.TrackArtists
import com.deathsdoor.astroplayer.ui.text.TrackTitle
import com.deathsdoor.chillback.common.ui.modifiers.fillFractionalSize
import com.deathsdoor.chillback.common.ui.providers.GlobalAstroPlayerState


@Composable
internal actual fun ExtendedMediaPlayer(){
    val state = GlobalAstroPlayerState.current

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "PLAYING FROM ALBUM")

            //TODO make this a function itself
            Text(text = state.currentMediaItemMetadata?.albumTitle ?: "No Given Album Title")
        }

        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Button to go to extra song options"
        )
    }


    TrackArtwork(
        modifier = Modifier
            .fillFractionalSize(0.3f)
            .padding(16.dp),
        state = state
    )


    Column(modifier = Modifier.padding(start = 8.dp)) {
        TrackTitle(
            state = state,
            style = MaterialTheme.typography.headlineMedium
        )

        TrackArtists(
            state = state,
            style = MaterialTheme.typography.titleLarge
        )
    }

    DurationSlider(state = state)

    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        LikeButton(state = state, onLikeStatusChanged = {
            //TODO
        })

        PreviousMediaItemButton(state = state)

        PlayPauseButton(state = state)

        NextMediaItemButton(state = state)

        //TODO add shuffle and repeat UI in it and in astroplayer-ui module
    }

    //TODO lyric view add it
    //TODO add equalizer , queue
}