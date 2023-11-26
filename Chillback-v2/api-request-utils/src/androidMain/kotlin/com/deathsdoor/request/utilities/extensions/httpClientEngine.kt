package com.deathsdoor.request.utilities.extensions

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual val httpClientEngine: HttpClientEngine by lazy{ OkHttp.create() }