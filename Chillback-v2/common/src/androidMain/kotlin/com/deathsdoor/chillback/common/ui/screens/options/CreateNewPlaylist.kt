package com.deathsdoor.chillback.common.ui.screens.options

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

//TODO just update this
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal actual fun CreateNewPlaylist(onFinish :@Composable (title : String) -> Unit){
    var isDialogDimissed by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    //TODO use same thing for loginscreen
    val isError by remember(title) { mutableStateOf(title.isEmpty()) }

    if(isDialogDimissed) {
        if(!isError) onFinish(title)
        return
    }

    AlertDialog(
        onDismissRequest = {

            isDialogDimissed = true
        },
        title = {
            Text("Enter new playlist title")
        },
        text = {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                isError = isError,
                singleLine = true
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if(!isError) {
                        isDialogDimissed = true
                        return@Button
                    }
                    //TODO show msg if isError == true
                },
                content = {
                    Text("Next")
                }
            )
        }
    )
}