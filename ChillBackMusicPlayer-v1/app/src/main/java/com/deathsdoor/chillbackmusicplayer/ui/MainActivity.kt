package com.deathsdoor.chillbackmusicplayer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Metadata
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Song
import com.deathsdoor.chillbackmusicplayer.data.music.AudioFileDetector
import com.deathsdoor.chillbackmusicplayer.data.music.JAudioTagger
import com.deathsdoor.chillbackmusicplayer.data.music.MusixSpiele
import com.deathsdoor.chillbackmusicplayer.data.notifications.MusixSpielerNotification
import com.deathsdoor.chillbackmusicplayer.data.notifications.NotificationConstants
import com.deathsdoor.chillbackmusicplayer.data.permissions.PermissionHandler
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.updateValue
import com.deathsdoor.chillbackmusicplayer.ui.components.TriggerWhenUserLoggedOut
import com.deathsdoor.chillbackmusicplayer.ui.navigation.loginscreen.LoginScreen
import com.deathsdoor.chillbackmusicplayer.ui.screens.RootApplicationScreen
import com.deathsdoor.chillbackmusicplayer.ui.splashscreen.SplashScreen
import com.deathsdoor.chillbackmusicplayer.ui.theme.SetThemeFurApplication
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onDestroy() {
        super.onDestroy()
        MusixSpielerNotification(baseContext,NotificationConstants.musicNotificationID,NotificationConstants.musicChannelID).cancelNotification()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*TODO uncomment it once its finished */
           // SetThemeFurApplication {
          //      SplashScreen(1500) { Text("SplashScreen") }
            //    TriggerWhenUserLoggedOut { LoginScreen() }
            //}
         //   if(Firebase.auth.currentUser == null) return@setContent

            val mainViewModel = remember { MainViewModel() }
            mainViewModel.musixSpiele = MusixSpiele(LocalContext.current)
            MusixSpielerNotification(LocalContext.current,NotificationConstants.musicNotificationID, NotificationConstants.musicChannelID).createPlayerNotification(mainViewModel.musixSpiele.exoplayer)

            SetThemeFurApplication { RootApplicationScreen(mainViewModel) }

            val context: Context = LocalContext.current

            //Detect local songs in background if got permission
            if(PermissionHandler.havePermission(context,PermissionHandler.Permission.READ_EXTERNAL_STORAGE)){
                mainViewModel.songsOnUserExternalStorage.updateValue(
                    AudioFileDetector.detectJederAudioFileOnExternalStorage(context).map {
                        val audioTag = JAudioTagger(it)
                        Song(
                            //TODO set isLiked in it
                            //TODO add all other fields to the metadata and update jaudiotagger to include all the metadata class fields
                            metadata = Metadata(
                                name = audioTag.title,
                                artist = audioTag.artist,
                                album = audioTag.album,
                                albumArtist = audioTag.albumArtist,
                                genre = audioTag.genre,
                                year = audioTag.year.toInt(),
                            )
                        )
                    })
            }
        }
    }
}
