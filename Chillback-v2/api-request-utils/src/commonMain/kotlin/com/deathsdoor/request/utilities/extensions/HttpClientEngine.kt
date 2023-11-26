package com.deathsdoor.request.utilities.extensions

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*

expect val httpClientEngine : HttpClientEngine
