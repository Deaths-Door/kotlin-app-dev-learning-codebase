package com.deathsdoor.chillbackmusicplayer.ui.components

import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun TriggerWhenUserLoggedOut(contentLoggedOut : @Composable () -> Unit){
    var isLoggedIn by remember { mutableStateOf(false) }
    DisposableEffect(key1 = Firebase.auth) {
        val firebaseAuthListener = FirebaseAuth.AuthStateListener { isLoggedIn = it.currentUser != null }
        Firebase.auth.addAuthStateListener(firebaseAuthListener)
        onDispose {
            Firebase.auth.removeAuthStateListener(firebaseAuthListener)
        }
    }
    if(!isLoggedIn) contentLoggedOut()
}