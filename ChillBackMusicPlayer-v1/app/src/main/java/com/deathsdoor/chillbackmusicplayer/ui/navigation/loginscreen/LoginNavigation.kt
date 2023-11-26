package com.deathsdoor.chillbackmusicplayer.ui.navigation.loginscreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

sealed class LoginNavigation private constructor(val route : Route){
    enum class Route{
        MainScreen,
        UserLogIn,
        UserSignIn,
    }
    companion object {
        val alle: List<LoginNavigation> get() = listOf(MainScreen, UserLogIn, UserSignIn)
    }

    abstract fun content(navController: NavController): @Composable () -> Unit
    private object MainScreen : LoginNavigation(Route.MainScreen){
        override fun content(navController: NavController):@Composable () -> Unit = { MainScreen() }
    }
    private object UserLogIn : LoginNavigation(Route.UserLogIn){
        override fun content(navController: NavController):@Composable () -> Unit = { UserLogIn() }
    }
    private object UserSignIn : LoginNavigation(Route.UserSignIn){
        override fun content(navController: NavController):@Composable () -> Unit = {  UserSignIn() }
    }
}
