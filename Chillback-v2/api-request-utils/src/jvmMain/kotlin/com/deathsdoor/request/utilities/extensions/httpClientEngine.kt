package com.deathsdoor.request.utilities.extensions

import io.ktor.client.engine.HttpClientEngine

actual val httpClientEngine: HttpClientEngine by lazy{ CIO.create() }