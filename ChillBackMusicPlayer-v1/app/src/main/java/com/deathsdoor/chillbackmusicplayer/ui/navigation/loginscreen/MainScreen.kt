package com.deathsdoor.chillbackmusicplayer.ui.navigation.loginscreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.extensions.isValidEmail
import com.deathsdoor.chillbackmusicplayer.ui.components.SignInButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MainScreen(){
    Column(Modifier.wrapContentSize()){
        var email by remember { mutableStateOf("") }
        OutlinedTextField(
            value = email,
            onValueChange ={ email = it},
            label={ Text("Enter Email") },
            placeholder = { Text("example@gmail.com") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick={
                //TODO show it to user what happened
                if(!email.isValidEmail()){
                    Log.d("Login","Not Valid Email")
                    return@Button
                }

                Firebase.auth.fetchSignInMethodsForEmail(email).addOnCompleteListener{
                    if(!it.isSuccessful){
                        Log.d("Login","Not successful due to errors ")
                        return@addOnCompleteListener
                    }

                    if(it.result.signInMethods?.isEmpty() == false){
                        Log.d("Login","Not successful as email has an account")
                        return@addOnCompleteListener
                    }

                    Log.d("Login","Successful as email doesn't have an account")
                }
            }){
            Text("Continue")
        }
        Text("or",modifier = Modifier.align(Alignment.CenterHorizontally))
        SignInButton(
            painter = painterResource(id = R.drawable.image_google_logo),
            service = "Google"
        ) {

        }
        SignInButton(
            painter = painterResource(id = R.drawable.image_google_logo),
            service = "Other TODO"
        ) {

        }
        SignInButton(
            painter = painterResource(id = R.drawable.image_google_logo),
            service = "Other TODO"
        ) {

        }
    }
}