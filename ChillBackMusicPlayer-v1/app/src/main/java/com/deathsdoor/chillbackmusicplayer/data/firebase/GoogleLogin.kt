package com.deathsdoor.chillbackmusicplayer.data.firebase

import android.app.Activity
import android.content.Intent
import com.deathsdoor.chillbackmusicplayer.data.randomNumber
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleLogin(private val activity: Activity, val requestCode:Int = randomNumber) {
    fun initiateSignIn(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        activity.startActivityForResult(GoogleSignIn.getClient(activity, gso).signInIntent,requestCode)
    }
    inline fun handleSignInResult(requestCode: Int, data: Intent?, crossinline action:(isSuccessful:Boolean, message:String?) -> Unit){
        if (requestCode != this.requestCode) return

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        if(task.isSuccessful){
            try {
                val account = task.getResult(ApiException::class.java)
                val googleAuthCredential = GoogleAuthProvider.getCredential(account?.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(googleAuthCredential)
                    .addOnSuccessListener { action(true,null) }
                    .addOnCompleteListener { if(!it.isSuccessful) action(false, it.exception?.localizedMessage) }
            } catch (e: ApiException) { action(false, e.localizedMessage) }
        }
        else action(false, task.exception?.localizedMessage)
    }
}