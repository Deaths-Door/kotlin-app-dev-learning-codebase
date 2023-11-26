package com.deathsdoor.chillbackmusicplayer.data.extensions.export

import android.view.View

object ClickListeners {
    @Suppress("UNCHECKED_CAST")
    inline fun <T: View> T.onClick(crossinline action:(T) -> Unit) = setOnClickListener { action(it as T) }
    @Suppress("UNCHECKED_CAST")
    inline fun <T: View> T.onLongClick(crossinline action:(T) -> Boolean) = setOnLongClickListener { action(it as T) }
}