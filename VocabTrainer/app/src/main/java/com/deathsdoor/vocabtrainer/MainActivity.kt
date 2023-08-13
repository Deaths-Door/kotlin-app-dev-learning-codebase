package com.deathsdoor.vocabtrainer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview


class MainActivity : AppCompatActivity() {
    @Preview
    @Composable
    fun DefaultScreen() {
        Column {
            val context = LocalContext.current
            Text("Hello World")
            Button(onClick = {
                Toast.makeText(context,"FUCK OFF",Toast.LENGTH_SHORT).show()
            }
            ) {
                Text("Click me")
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultScreen()
        }
    }
}