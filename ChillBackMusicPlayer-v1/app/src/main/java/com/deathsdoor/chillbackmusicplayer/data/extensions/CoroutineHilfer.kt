package com.deathsdoor.chillbackmusicplayer.data.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class CoroutineHilfer {
    companion object{
        suspend inline fun <T> onMainThread(crossinline action: suspend () -> T): T = withContext(Dispatchers.Main) { action() }
        suspend inline fun <T> onBackgroundThread(crossinline action: suspend () -> T): T = withContext(Dispatchers.IO) { action() }
        suspend inline fun <T> onDefaultThread(crossinline action: suspend () -> T): T = withContext(Dispatchers.Default) { action() }
        suspend inline fun <T> onUnconfinedThread(crossinline action: suspend () -> T): T = withContext(Dispatchers.Unconfined) { action() }
    }
}