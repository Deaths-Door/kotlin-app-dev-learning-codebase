package com.deathsdoor.chillback.common.ui.components.buttons

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.deathsdoor.chillback.common.resources.image.GoogleLogo
import com.deathsdoor.chillback.common.ui.components.image.ClickableIcon
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status

@Composable
internal fun GoogleLoginButton(modifier : Modifier = Modifier, onSignInResult: (GoogleSignInAccount) -> Unit,onFailed : (exception : ApiException) -> Unit){
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode != Activity.RESULT_OK) onFailed(ApiException(Status(result.resultCode)))
            try { onSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data).getResult(ApiException::class.java)) }
            catch (exception: ApiException) { onFailed(exception) }
        }
    )

    val context = LocalContext.current
    ClickableIcon(
        modifier = modifier,
        imageVector = Icons.GoogleLogo,
       // imageVector = Icons.Default.GoogleLogo,
        contentDescription = "Google Sign-In",
        onClick = {
            launcher.launch(GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN).signInIntent)
        }
    )
}