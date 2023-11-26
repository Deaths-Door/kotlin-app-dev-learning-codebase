package com.deathsdoor.chillback.common.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.deathsdoor.chillback.common.data.firebase.InitializeFirebase
import com.deathsdoor.chillback.common.data.firebase.userPlaylists
import com.deathsdoor.chillback.common.ui.components.InitializeSnackbars
import com.deathsdoor.chillback.common.ui.providers.InitializeGlobalProviders
import com.deathsdoor.chillback.common.ui.screens.login.LoginScreen
import com.deathsdoor.chillback.common.ui.screens.splashscreen.SplashScreen
import com.deathsdoor.chillback.common.ui.screens.splashscreen.SplashScreenContent
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import com.deathsdoor.chillback.common.data.music.ExtractMatchMusicFiles
import com.deathsdoor.chillback.common.ui.providers.GlobalViewModel
import com.deathsdoor.chillback.common.ui.theme.Theme

@NonRestartableComposable
@Composable
fun AppScreen() {
    InitializeGlobalProviders {
       var isFirebaseAccessible by rememberSaveable { mutableStateOf(false) }

       if(!isFirebaseAccessible){
            SplashScreenContent()
            InitializeFirebase()
            isFirebaseAccessible = true
            return@InitializeGlobalProviders
       }

        Theme.BasedOnSettings {
            var isUserLoggedIn by remember { mutableStateOf(Firebase.auth.currentUser != null) }

            if(!isUserLoggedIn) LoginScreen()
            else {
                ExtractMatchMusicFiles()

                var haveShownSplashScreen by rememberSaveable { mutableStateOf(false) }

                if(haveShownSplashScreen) Screen()
                else SplashScreen {
                    haveShownSplashScreen = true
                    Screen()
                }
            }

            val viewmodel = GlobalViewModel.current

            //TODO combine this one with the Theme.BasedOnSettings.LaunchedEffect
            LaunchedEffect(Firebase.auth.currentUser){
                isUserLoggedIn = Firebase.auth.currentUser != null

                if(Firebase.auth.currentUser == null) return@LaunchedEffect

                //All User Playlists
                Firebase.userPlaylists {
                    viewmodel.userPlaylistsOnCloud.value = it.toMutableList()
                }

                //Match firestore playlist with device songs as this data is not uploaded
                viewmodel.songsOnUserDevice.value.forEach { song ->
                    viewmodel.userPlaylistsOnCloud.value
                        .filter { playlist -> playlist.songs.any { it.id == song.id } }
                        .forEach { playlist -> playlist.songs.add(song) }
                }
            }

            InitializeSnackbars()
        }
    }
}

@Composable
internal expect fun Screen()