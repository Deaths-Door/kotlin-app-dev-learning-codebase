package com.deathsdoor.chillback.common.ui.components.mediaplayer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deathsdoor.astroplayer.ui.buttons.LikeButton
import com.deathsdoor.astroplayer.ui.buttons.PlayPauseButton
import com.deathsdoor.astroplayer.ui.images.TrackArtwork
import com.deathsdoor.astroplayer.ui.sliders.DurationSlider
import com.deathsdoor.astroplayer.ui.text.TrackArtists
import com.deathsdoor.astroplayer.ui.text.TrackTitle
import com.deathsdoor.chillback.common.ui.providers.GlobalAstroPlayerState
import eu.wewox.modalsheet.ExperimentalSheetApi
import eu.wewox.modalsheet.ModalSheet

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSheetApi::class)
@Composable
internal actual fun CollapsedMediaPlayer(modifier : Modifier){
    val astroPlayerState = GlobalAstroPlayerState.current
    var isExtendedMediaPlayerVisible by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(8.dp),
        onClick = {
            isExtendedMediaPlayerVisible = true
        },
        content = {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TrackArtwork(state = astroPlayerState)

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    TrackTitle(state = astroPlayerState)
                    TrackArtists(state = astroPlayerState)
                }

                Spacer(modifier = Modifier.width(16.dp))

                LikeButton(state = astroPlayerState, onLikeStatusChanged = {
                    //TODO
                })

                Spacer(modifier = Modifier.width(16.dp))

                PlayPauseButton(state = astroPlayerState)
            }

            //TODO use https://github.com/SmartToolFactory/Compose-Colorful-Sliders for this
            DurationSlider(state = astroPlayerState)
        }
    )

    ModalSheet(
        visible = isExtendedMediaPlayerVisible,
        onVisibleChange = {
            isExtendedMediaPlayerVisible = it
        },
        content = {
            ExtendedMediaPlayer()
        }
    )
}