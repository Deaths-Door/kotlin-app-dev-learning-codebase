package com.deathsdoor.chillback.common.ui.providers

import androidx.compose.runtime.compositionLocalOf
import com.deathsdoor.chillback.common.data.viewmodel.GlobalViewModel

internal val GlobalViewModel = compositionLocalOf<GlobalViewModel> { error("Globalviewmodel is not provided") }
