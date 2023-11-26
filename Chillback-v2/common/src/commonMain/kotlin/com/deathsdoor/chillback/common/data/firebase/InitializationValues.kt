package com.deathsdoor.chillback.common.data.firebase

import androidx.compose.runtime.Composable
import dev.gitlive.firebase.Firebase


internal val Firebase.databaseUrl : String get() = "https://chillbackmusicplayer-default-rtdb.firebaseio.com"
internal val Firebase.storageBucket : String get() = "chillbackmusicplayer.appspot.com"
internal val Firebase.projectId : String get() = "chillbackmusicplayer"

internal expect val Firebase.applicationId : String
internal expect val Firebase.apiKey : String

@Composable
internal expect fun InitializeFirebase()