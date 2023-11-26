package com.deathsdoor.chillback.common.ui.providers

import androidx.compose.runtime.compositionLocalOf
import com.deathsdoor.astroplayer.state.AstroPlayerState

//TODO change all Global to local compositionLocalOf
internal val GlobalAstroPlayerState = compositionLocalOf<AstroPlayerState> { error("Global AstroPlayerState is not provided") }