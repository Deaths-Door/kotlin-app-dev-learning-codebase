package com.deathsdoor.chillback.common.ui.providers
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.CoroutineScope

internal val GlobalCoroutineScope = compositionLocalOf<CoroutineScope> { error("Global coroutineScope is not provided") }