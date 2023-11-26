package com.deathsdoor.chillback.common.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.deathsdoor.chillback.common.data.extensions.isValidEmail
import com.deathsdoor.chillback.common.data.secrets.ApiKeys
import com.deathsdoor.chillback.common.resources.MR
import com.deathsdoor.chillback.common.ui.components.buttons.GoogleLoginButton
import com.deathsdoor.chillback.common.ui.components.image.AdvancedImage
import com.deathsdoor.chillback.common.ui.providers.GlobalCoroutineScope
import com.deathsdoor.chillback.common.ui.providers.GlobalErrorSnackbarState
import com.deathsdoor.chillback.common.ui.providers.GlobalSuccessSnackbarState
import com.deathsdoor.password.strength.checker.PasswordStrengthChecker
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal actual fun LoginScreen() {
    val globalCoroutineScope = GlobalCoroutineScope.current
    val coroutineScope = rememberCoroutineScope()

    val errorSnackbarHostState = GlobalErrorSnackbarState.current
    val successSnackbarHostState = GlobalSuccessSnackbarState.current

    val passwordStrengthChecker by remember { lazy { PasswordStrengthChecker(ApiKeys.rapidApi) } }

    var isLoginShown by remember { mutableStateOf(true) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isValidEmail by remember { mutableStateOf(false) }
    var isPasswordStrongEnough by remember { mutableStateOf(false) }

    val invalidEmail = stringResource(MR.strings.login_screen_error_invalid_email)
    val weakPassword = stringResource(MR.strings.login_screen_error_weak_password)
    val successCreateAccount = stringResource(MR.strings.login_screen_success_account_created)
    val accountNotExist = stringResource(MR.strings.login_screen_error_account_not_exist)
    val successLogin = stringResource(MR.strings.login_screen_success_login)
    val unsuccessLogin = stringResource(MR.strings.login_screen_unsuccess_login)

    //TODO check if another box is needed as its being called froma
    Box {
        AdvancedImage(
            modifier = Modifier.fillMaxSize(),
            image = "https://i.pinimg.com/originals/b9/c9/89/b9c989c25792ab5094e27170c18eb015.jpg",
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = title(isLoginShown),
                style = MaterialTheme.typography.displayMedium,
            )

            Spacer(modifier = Modifier.height(16.dp))

            if(isLoginShown){
                Text(
                    text = "Please sign-in to continue",
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            val fillAlmostMaxWidthModifier = Modifier.fillMaxWidth(0.8f)
            OutlinedTextField(
                modifier = fillAlmostMaxWidthModifier,
                value = email,
                onValueChange = {
                    email = it
                    isValidEmail = email.isNotEmpty() && email.isValidEmail()
                },
                label = { Text(text = stringResource(MR.strings.login_screen_email)) },
                leadingIcon = {
                    Image(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                } ,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = if(email.isEmpty()) false else !isValidEmail,
                singleLine = true
            )
    
            Spacer(modifier = Modifier.height(16.dp))
    
            OutlinedTextField(
                modifier = fillAlmostMaxWidthModifier,
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text(text = stringResource(MR.strings.login_screen_password)) },
                leadingIcon = {
                    Image(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                } ,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = MaterialTheme.typography.bodyMedium,
                isError = if(password.isEmpty()) false else !isPasswordStrongEnough,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoginShown) {
                var isChecked by remember { mutableStateOf(false)}
                Row(
                    modifier = fillAlmostMaxWidthModifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = {
                            isChecked = it
                        }
                    )
                    Text(text = stringResource(MR.strings.login_screen_remember_me))
                }
            }
    
            Button(
                modifier = fillAlmostMaxWidthModifier,
                content = { Text(text = title(isLoginShown = isLoginShown)) },
                onClick = {
                    coroutineScope.launch localScope@ {
                        if(!isValidEmail) {
                            errorSnackbarHostState.showSnackbar(invalidEmail)
                            return@localScope
                        }

                        isPasswordStrongEnough = true
                        //TODO enable this again in the future
                     //   isPasswordStrongEnough = passwordStrengthChecker.check(password) == Strength.Strong
    
                        if(!isPasswordStrongEnough) {
                            errorSnackbarHostState.showSnackbar(weakPassword)
                            return@localScope
                        }
    
                        globalCoroutineScope.launch globalScope@ {
                            if(!isLoginShown){
                                Firebase.auth.createUserWithEmailAndPassword(email = email,password =  password)
                                coroutineScope.launch {
                                    successSnackbarHostState.showSnackbar(successCreateAccount)
                                }
                                return@globalScope
                            }
    
                            if(Firebase.auth.fetchSignInMethodsForEmail(email).isEmpty()){
                                coroutineScope.launch {
                                    errorSnackbarHostState.showSnackbar(accountNotExist)
                                }
                                return@globalScope
                            }
    
                            try {
                                Firebase.auth.signInWithEmailAndPassword(email = email, password = password)
                                coroutineScope.launch {
                                    successSnackbarHostState.showSnackbar(successLogin)
                                }
                            }
                            catch(expection : Exception){
                                coroutineScope.launch {
                                    errorSnackbarHostState.showSnackbar(unsuccessLogin)
                                }
                            }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if(isLoginShown){
                ClickableText(
                    text = buildAnnotatedString {
                        val string = stringResource(MR.strings.login_screen_forgot_password)
                        append(string)
                    },
                    style = MaterialTheme.typography.titleMedium,
                    onClick = {
                        globalCoroutineScope.launch {
                            Firebase.auth.sendPasswordResetEmail(email)
                        }
                    }
                )
    
                Spacer(modifier = Modifier.height(8.dp))
    
                Divider(
                    modifier = fillAlmostMaxWidthModifier,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    thickness = 4.dp
                )
                
                GoogleLoginButton(
                    onSignInResult = {
                        globalCoroutineScope.launch {
                            Firebase.auth.signInWithCustomToken(it.idToken!!)
                        }
                    },
                    onFailed = { exception ->
                        coroutineScope.launch {
                            errorSnackbarHostState.showSnackbar(exception.localizedMessage)
                        }
                    }
                )
            }
    
            Row {
                Text(text = stringResource(if(isLoginShown) MR.strings.login_screen_already_have_account else MR.strings.login_screen_dont_have_account))

                Spacer(modifier = Modifier.width(8.dp))

                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                            append(title(isLoginShown = !isLoginShown))
                        }
                    },
                    onClick = {
                        isLoginShown = !isLoginShown
                    }
                )
            }
        }
    }
   
}

@Composable
private fun title(isLoginShown : Boolean) = if(isLoginShown) stringResource(MR.strings.login_screen_login) else stringResource(MR.strings.login_screen_signin)