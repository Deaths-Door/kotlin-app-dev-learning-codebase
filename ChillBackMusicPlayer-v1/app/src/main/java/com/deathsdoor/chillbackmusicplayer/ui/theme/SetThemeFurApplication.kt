package com.deathsdoor.chillbackmusicplayer.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

//TODO add if else to set theme
@Composable
fun SetThemeFurApplication(content:@Composable () -> Unit){
    DunkelTheme(content)
}
private val Purple200 = Color(0xFFBB86FC)
private val Purple500 = Color(0xFF6200EE)
private val Purple700 = Color(0xFF3700B3)
private val Teal200 = Color(0xFF03DAC6)


//TODO update dark theme
@Composable
fun DunkelTheme(content: @Composable() () -> Unit){
    MaterialTheme(
        colors = darkColors(
            primary = Purple200,
            primaryVariant = Purple700,
            secondary = Teal200
        ),
        typography= Typography(
            h1 = TextStyle(fontSize = 24.sp),
            h2 = TextStyle(fontSize = 18.sp),
            h3 = TextStyle(fontSize = 16.sp),
            h4 = TextStyle(fontSize = 14.sp),
            h5 = TextStyle(fontSize = 12.sp),
            h6 = TextStyle(fontSize = 10.sp),
            subtitle1 = TextStyle(fontSize = 16.sp),
            subtitle2 = TextStyle(fontSize = 14.sp),
            body1 = TextStyle(fontSize = 16.sp),
            body2 = TextStyle(fontSize = 14.sp),
            button = TextStyle(fontSize = 14.sp),
            caption = TextStyle(fontSize = 12.sp)
        ) ,
        content = content
    )
}