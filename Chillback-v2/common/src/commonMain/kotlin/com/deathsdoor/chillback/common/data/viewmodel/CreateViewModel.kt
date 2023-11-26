package com.deathsdoor.chillback.common.data.viewmodel

import androidx.compose.runtime.Composable
import com.hoc081098.kmp.viewmodel.ViewModel as ExternalViewModel

@Composable
internal expect inline fun <reified T : ExternalViewModel> createViewModel(crossinline action : () -> T) : T