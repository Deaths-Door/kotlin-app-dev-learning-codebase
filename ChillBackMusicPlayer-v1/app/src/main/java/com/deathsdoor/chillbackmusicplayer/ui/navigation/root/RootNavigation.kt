package com.deathsdoor.chillbackmusicplayer.ui.navigation.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.deathsdoor.chillbackmusicplayer.R.drawable

//TODO add vector asset ids
sealed class RootNavigation private constructor(val route : Route, val image: Int){
    companion object {
        val alle: List<RootNavigation> get() = listOf(UserLibrary,
            ExploreNewContent,
            EventMaps,
            Games)
    }

    enum class Route{
        UserLibrary,
        ExploreNewContent,
        EventMaps,
        Games
    }

    abstract fun content(navController: NavController): @Composable () -> Unit

    private object UserLibrary : RootNavigation(Route.UserLibrary,drawable.ic_launcher_background){
        override fun content(navController: NavController): @Composable () -> Unit = { UserLibrary(navController) }
    }

    private object ExploreNewContent : RootNavigation(Route.ExploreNewContent,drawable.ic_launcher_background){
        override fun content(navController: NavController): @Composable () -> Unit = { ExploreNewContent(navController) }
    }

    private object EventMaps : RootNavigation(Route.EventMaps,drawable.ic_launcher_background){
        override fun content(navController: NavController): @Composable () -> Unit = { EventMaps(navController) }
    }

    private object Games : RootNavigation(Route.Games,drawable.ic_launcher_background){
        override fun content(navController: NavController): @Composable () -> Unit = { Games(navController) }
    }
}