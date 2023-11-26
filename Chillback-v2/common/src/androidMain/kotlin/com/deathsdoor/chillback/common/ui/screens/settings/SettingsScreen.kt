package com.deathsdoor.chillback.common.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.deathsdoor.chillback.common.data.extensions.open
import com.deathsdoor.chillback.common.data.settings.Settings
import com.deathsdoor.chillback.common.data.settings.SettingsCategory
import com.deathsdoor.chillback.common.data.settings.Type
@Composable
internal actual fun Settings.Companion.Screen(){
    val settings = mutableListOf<SettingsCategory>().apply {
        Settings.values().forEach { add(it.category()) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp,end = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp,bottom = 16.dp),
            text = "Settings",
            style = MaterialTheme.typography.displayMedium
        )


        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(settings){
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineMedium
                )

                Divider(thickness = 4.dp)

                it.items.forEach option@ { (name,description,type) ->
                    if(type is Type.Custom){
                        type.content()

                        Divider(thickness = 4.dp)
                        return@option
                    }

                    @Composable
                    fun Item(){
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleMedium,
                        )

                        Divider(thickness = 4.dp)

                        Text(
                            text = description,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    if(type is Type.Info) Item()
                    else {
                        val context = LocalContext.current
                        Box(modifier = Modifier
                            .clickable {
                                (type as Type.OpenWebsite).open(context)
                            }
                        ){
                            Column {
                                Item()
                            }
                        }
                    }
                }

            }
        }
    }
}