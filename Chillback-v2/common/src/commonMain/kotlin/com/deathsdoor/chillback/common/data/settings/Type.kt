package com.deathsdoor.chillback.common.data.settings

import androidx.compose.runtime.Composable

internal sealed class Type {
    object Info : Type()

    class OpenWebsite(val url : String) : Type()

    class Custom(val content : (@Composable () -> Unit)) : Type()
}