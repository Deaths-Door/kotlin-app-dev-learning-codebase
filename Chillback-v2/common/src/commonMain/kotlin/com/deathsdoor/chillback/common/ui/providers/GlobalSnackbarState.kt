package com.deathsdoor.chillback.common.ui.providers

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.material3.SnackbarHostState

internal val GlobalErrorSnackbarState = compositionLocalOf<SnackbarHostState> { error("SnackbarState for errors is not provided") }
internal val GlobalSuccessSnackbarState = compositionLocalOf<SnackbarHostState> { error("SnackbarState for successes is not provided") }
internal val GlobalInfoSnackbarState = compositionLocalOf<SnackbarHostState> { error("SnackbarState for information is not provided") }