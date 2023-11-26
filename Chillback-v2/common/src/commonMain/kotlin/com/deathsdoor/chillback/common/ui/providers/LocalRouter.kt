package com.deathsdoor.chillback.common.ui.providers

import androidx.compose.runtime.compositionLocalOf
import io.github.xxfast.decompose.router.Router

@Deprecated("USE THE ONE GIVEN IN THE DEFAULT ROUTER PACKAGE, AND PASS ROUTER TILL ROUTED CONTENT COMPOSABLE IS CALLED")
internal val LocalRouter = compositionLocalOf<Router<*>> { error("Global Router<RootGraph> is not provided") }