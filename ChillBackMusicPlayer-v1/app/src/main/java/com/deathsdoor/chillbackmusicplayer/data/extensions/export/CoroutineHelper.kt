package com.deathsdoor.chillbackmusicplayer.data.extensions.export

import kotlinx.coroutines.*
import kotlinx.coroutines.withTimeoutOrNull as defaultNullTimeOut

abstract class CoroutineHelper {
    companion object {
        suspend inline fun <T> onMainThread(crossinline action: suspend () -> T): T = withContext(Dispatchers.Main) { action() }
        suspend inline fun <T> onBackgroundThread(crossinline action: suspend () -> T): T = withContext(Dispatchers.IO) { action() }
        suspend inline fun <T> onDefaultThread(crossinline action: suspend () -> T): T = withContext(Dispatchers.Default) { action() }
        suspend inline fun <T> onUnconfinedThread(crossinline action: suspend () -> T): T = withContext(Dispatchers.Unconfined) { action() }
        suspend inline fun <T> awaitAll(tasks: Collection<Deferred<T>>): List<T> = coroutineScope{ tasks.map { async { it.await() } } }.awaitAll()
        suspend inline fun <T> withTimeoutOrNull(timeMillis: Long, noinline block: CoroutineScope.() -> T): T? = defaultNullTimeOut(timeMillis,block)
        suspend inline fun <T> retry(times: Int = Int.MAX_VALUE, initialDelay: Long = 100L, maxDelay: Long = 1000L, factor: Double = 2.0, jitter: Double = 0.5, shouldRetry: (Throwable) -> Boolean = { true }, crossinline block: suspend () -> T): suspend () -> T{
            var currentDelay = initialDelay
            var exception : Throwable? = null
            repeat(times - 1) {
                try {
                    return { block() }
                } catch (e: Throwable) {
                    if (!shouldRetry(e)) throw e
                    exception = e
                }
                delay(currentDelay)

                // Calculate the next delay using a backoff algorithm
                val jitterValue = currentDelay * jitter
                currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
                currentDelay = (currentDelay - jitterValue + 2 * jitterValue * Math.random()).toLong()
            }
            throw exception!!
        }
    }
}

suspend fun main() {
    val x = CoroutineHelper.retry{
        return@retry "HelloAsd"
    }
}