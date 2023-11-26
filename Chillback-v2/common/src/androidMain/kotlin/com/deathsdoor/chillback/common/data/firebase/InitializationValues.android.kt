package com.deathsdoor.chillback.common.data.firebase

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize

internal actual val Firebase.applicationId: String get() = "1:1088062845722:android:71fa8d5729dbaa80c072a3"
internal actual val Firebase.apiKey: String get() = "AIzaSyBhltSaliqzAP9NSluLmp4yyvhQTmi3HwA"

@Composable
internal actual fun InitializeFirebase() {
    Firebase.initialize(
        context = LocalContext.current,
        options = FirebaseOptions (
            applicationId = Firebase.applicationId,
            apiKey = Firebase.apiKey,
            databaseUrl = Firebase.databaseUrl,
            projectId = Firebase.projectId,
            storageBucket = Firebase.storageBucket,
        )
    )
}