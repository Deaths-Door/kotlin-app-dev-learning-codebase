package com.deathsdoor.chillback.common.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.essenty.parcelable.Parcelable

sealed interface Route :  Parcelable {
    val image : ImageVector? get() = null

    //TODO remove this vararg args in the future
    @Composable
    fun content(vararg args : Any)
}
