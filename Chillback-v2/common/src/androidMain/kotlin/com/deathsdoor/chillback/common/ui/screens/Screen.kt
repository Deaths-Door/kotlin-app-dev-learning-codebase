package com.deathsdoor.chillback.common.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.router.stack.push
import com.deathsdoor.chillback.common.data.permissions.Permissions
import com.deathsdoor.chillback.common.ui.components.mediaplayer.CollapsedMediaPlayer
import com.deathsdoor.chillback.common.ui.navigation.RootGraph
import com.deathsdoor.chillback.common.ui.navigation.Route
import com.deathsdoor.chillback.common.ui.providers.LocalRouter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import io.github.xxfast.decompose.router.Router
import io.github.xxfast.decompose.router.content.RoutedContent

//TODO make everything as common actual expect as possible
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class,)
@Composable
internal actual fun Screen() {

    val router = LocalRouter.current as Router<Route>

    val locationPermissionState = if(Permissions.canAccessLocation(LocalContext.current)) null else rememberPermissionState(permission = Permissions.accessLocation)
    val readExternalStoragePermissionState = if(Permissions.canReadExternalStorage(LocalContext.current)) null else rememberPermissionState(permission = Permissions.readExternalStorage)

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    RootGraph.values().forEach {
                        NavigationBarItem(
                            modifier = Modifier.fillMaxHeight(),
                            selected = router.stack.value.active.configuration == it,
                            icon = { Icon(imageVector = it.image!!, contentDescription = null) },
                            onClick = {
                                if(it == RootGraph.LocalMusic && readExternalStoragePermissionState?.status?.isGranted == false){
                                    readExternalStoragePermissionState.launchPermissionRequest()
                                    return@NavigationBarItem
                                }

                                if(it == RootGraph.EventMaps && locationPermissionState?.status?.isGranted == false){
                                    locationPermissionState.launchPermissionRequest()
                                    return@NavigationBarItem
                                }

                                router.push(it)

                                Log.d("ROUTER","backStack = ${router.stack.value.backStack}")
                                Log.d("ROUTER","active = ${router.stack.value.active}")
                            }
                        )
                    }
                }
            )
        },
        content = { value ->
            Box(modifier = Modifier.padding(value).fillMaxSize()) {
                RoutedContent(
                    router = router,
                    content = {
                        it.content()
                    }
                )

                if(router.stack.value.active.configuration == RootGraph.EventMaps) return@Box

                CollapsedMediaPlayer(modifier = Modifier.align(Alignment.BottomCenter))
            }
        }
    )
}