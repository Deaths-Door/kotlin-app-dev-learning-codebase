package com.deathsdoor.chillback

import androidx.compose.ui.window.Window
import com.deathsdoor.chillback.common.ui.screens.AppScreen
import org.jetbrains.skiko.wasm.onWasmReady

//TODO
// Also set up Firebase Hosting for this app at
// https://firebase.google.com/docs/hosting/?hl=en&authuser=0
// Firebase Hosting provides fast and secure hosting for your web app, static and dynamic content, and microservices.
fun main() = onWasmReady {
    Window("Chillback"){
        AppScreen()
    }
}