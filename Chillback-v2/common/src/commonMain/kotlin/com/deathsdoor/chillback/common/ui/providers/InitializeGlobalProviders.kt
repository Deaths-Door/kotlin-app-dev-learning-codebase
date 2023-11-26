package com.deathsdoor.chillback.common.ui.providers

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.deathsdoor.astroplayer.state.rememberAstroPlayerState
import com.deathsdoor.chillback.common.data.viewmodel.GlobalViewModel
import com.deathsdoor.chillback.common.data.viewmodel.createViewModel
import com.deathsdoor.chillback.common.ui.navigation.RootGraph
import com.deathsdoor.chillback.common.ui.navigation.Route
import io.github.xxfast.decompose.LocalComponentContext
import io.github.xxfast.decompose.router.rememberRouter

@Composable
internal fun InitializeGlobalProviders(content : @Composable () -> Unit){
    val componentContext = DefaultComponentContext(lifecycle = LifecycleRegistry())
    //TODO remove nested CompositionLocalProvider in future when LocalComponentContex issue can be solved wihout nested providers
    CompositionLocalProvider(LocalComponentContext provides componentContext){
        val coroutineScope = rememberCoroutineScope()

        val errorSnackbarHostState = remember { SnackbarHostState() }
        val successSnackbarHostState = remember { SnackbarHostState() }
        val infoSnackbarHostState = remember { SnackbarHostState() }

        val astroPlayerState = rememberAstroPlayerState()

        val globalViewModel = createViewModel { GlobalViewModel() }

        val router = rememberRouter(Route::class,listOf(RootGraph.UserLibrary))

        CompositionLocalProvider(
            GlobalCoroutineScope provides coroutineScope,
            GlobalErrorSnackbarState provides errorSnackbarHostState,
            GlobalSuccessSnackbarState provides successSnackbarHostState,
            GlobalInfoSnackbarState provides infoSnackbarHostState,
            GlobalViewModel provides globalViewModel,
            GlobalAstroPlayerState provides astroPlayerState,
            LocalRouter provides router,
            content = content
        )
    }

}