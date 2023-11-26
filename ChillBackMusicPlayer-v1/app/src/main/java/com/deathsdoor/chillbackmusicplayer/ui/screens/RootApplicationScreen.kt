package com.deathsdoor.chillbackmusicplayer.ui.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.ui.components.ExoPlayerMusixSpiele
import com.deathsdoor.chillbackmusicplayer.ui.navigation.root.RootNavigation

@Composable
fun RootApplicationScreen(mainViewModel: MainViewModel) {
    val navigationController = rememberNavController()
    ConstraintLayout {
        val (navigationHost,bottomNavigation,musixSpiele) = createRefs()
        NavHost(
            navController = navigationController,
            startDestination = RootNavigation.alle[0].route.name,
            modifier = Modifier.fillMaxHeight(0.9f).constrainAs(navigationHost) { top.linkTo(parent.top) }
        ) {
            RootNavigation.alle.forEach { destination ->
                composable(destination.route.name) { destination.content(navigationController)() }
            }
        }
        BottomNavigation(
            elevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
                .constrainAs(bottomNavigation) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(navigationHost.bottom)
                }
        ) {
            val navBackStackEntry by navigationController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            RootNavigation.alle.forEach {
                BottomNavigationItem(
                    icon = { Icon(imageVector = ImageVector.vectorResource(it.image), contentDescription = it.route.name) },
                    label = { Text(it.route.name) },
                    selected = currentRoute == it.route.name,
                    onClick = {
                        navigationController.navigate(it.route.name) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }

        //TODO cant see composable in view possible its beening hidden or the infalte root null is causing errors
        ExoPlayerMusixSpiele(
            musixSpiele = mainViewModel.musixSpiele,
            modifier = Modifier.padding(bottom = 8.dp).constrainAs(musixSpiele) {
                bottom.linkTo(bottomNavigation.top)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }
        )
    }
}