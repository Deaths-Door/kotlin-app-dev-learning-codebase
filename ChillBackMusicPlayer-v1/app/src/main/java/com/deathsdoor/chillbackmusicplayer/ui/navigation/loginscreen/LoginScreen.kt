package com.deathsdoor.chillbackmusicplayer.ui.navigation.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deathsdoor.chillbackmusicplayer.R

@Composable
fun LoginScreen() {
    Box(
        modifier =  Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Black.copy(alpha = 0.75f), RoundedCornerShape(16.dp)),
    ){
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Login Page Background",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
        )
        Card(
            elevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
               // .clip(RoundedCornerShape(16.dp))
                .fillMaxHeight(0.7f)
                .padding(8.dp)
                .background(Color.Cyan.copy(alpha = 0.75f), RoundedCornerShape(16.dp)),
        ){
            val navigationController = rememberNavController()
            NavHost(
                navController = navigationController,
                startDestination = LoginNavigation.alle[0].route.name,
                modifier = Modifier.fillMaxHeight(0.9f)
            ){
                LoginNavigation.alle.forEach { destination -> composable(destination.route.name) { destination.content(navigationController)() } }
            }
        }
    }
}

