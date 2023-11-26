package com.deathsdoor.chillback.common.data.viewmodel

import androidx.compose.runtime.Composable
import com.hoc081098.kmp.viewmodel.ViewModel as ExternalViewModel

//TODO handle this
@Composable
internal actual inline fun <reified T : ExternalViewModel> createViewModel(crossinline action: () -> T): T = action()/* viewModel(factory = object : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T = action() as T
})*/