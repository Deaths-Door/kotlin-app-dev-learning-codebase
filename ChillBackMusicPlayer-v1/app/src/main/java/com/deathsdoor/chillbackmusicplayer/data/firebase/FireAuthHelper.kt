package com.deathsdoor.chillbackmusicplayer.data.firebase

import com.google.firebase.auth.FirebaseAuth

class FireAuthHelper {
    interface OnLoginAttemptListener{
        fun onEmailInValid(email: String)
        fun onPasswordIsWeak(password: String)
        fun onSuccess()
        fun onFailure()
    }

    companion object{
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        fun String.validEmail(): Boolean
                = this.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())
        fun String.isPasswordStrong(): Boolean
                = this.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])(?=\\S+$).{8,}\$".toRegex())

        fun startEmailPasswordLogin(email: String, password: String, onLoginAttemptListener: OnLoginAttemptListener){
            if(!email.validEmail()) {
                onLoginAttemptListener.onEmailInValid(email)
                return
            }
            if(!password.isPasswordStrong()) {
                onLoginAttemptListener.onPasswordIsWeak(password)
                return
            }
            firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener { onLoginAttemptListener.onSuccess() }
                .addOnCompleteListener { if(!it.isSuccessful) onLoginAttemptListener.onFailure() }
        }

        fun resetPasswordEmail(email:String, onLoginAttemptListener: OnLoginAttemptListener){
            if(!email.validEmail()) {
                onLoginAttemptListener.onEmailInValid(email)
                return
            }
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener { onLoginAttemptListener.onSuccess() }
                .addOnCompleteListener { onLoginAttemptListener.onFailure() }
        }
    }
}