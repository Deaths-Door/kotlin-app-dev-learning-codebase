package com.deathsdoor.chillback.common.ui.screens.main

import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.deathsdoor.chillback.common.data.database.Caching
import com.deathsdoor.chillback.common.ui.components.image.AdvancedImage
import com.deathsdoor.chillback.common.ui.components.text.MarqueeText
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
internal actual fun EventMaps(){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState()

    val geocoder by remember { lazy { Geocoder(context) } }

    var name by remember { mutableStateOf("Loading") }

    Box(modifier = Modifier.fillMaxSize()){
        GoogleMap(
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                coroutineScope.launch {
                    val result = LocationServices.getFusedLocationProviderClient(context).lastLocation.await()
                    val latlng = LatLng(result.latitude,result.longitude)
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(latlng,10f)
                    name = currentLocationName(geocoder,latlng,10f)
                }
            },
            content = {
                Caching.ticketMasterEventsEntries.forEach {
                    Marker(
                        state = rememberMarkerState(
                            position = LatLng(it.geographicCoordinates.latitude,it.geographicCoordinates.longitude)
                        ),
                        title = it.basicData.name,
                        //icon =
                    )
                }
            }
        )
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            val cardColor = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f))
            val size = 48.dp

            Card(
                modifier = Modifier.size(size),
                shape = CircleShape,
                colors = cardColor,
                onClick = {  },
                content = {
                    AdvancedImage(
                        image = Firebase.auth.currentUser!!.android.photoUrl.toString(),
                        contentDescription = "Your Profile Picture",
                    )
                }
            )

            Card(
                modifier = Modifier.size(size),
                shape = CircleShape,
                colors = cardColor,
                onClick = {  },
                content = {
                    Image(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Button"
                    )
                }
            )

            Row(modifier = Modifier.weight(1f)){
                AdvancedImage(
                    image = "",
                    contentDescription = null,
                )
                MarqueeText(text = name)
            }

            Card(
                modifier = Modifier.size(size),
                colors = cardColor,
                onClick = { },
                content = {
                    Image(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings Button"
                    )
                }
            )
        }

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ){

            }
    }
}

private suspend fun currentLocationName(geocoder: Geocoder, latLng: LatLng, zoom : Float) : String {
    val location = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1)?.firstOrNull()
    return when {
        location == null -> "Error Getting Location"
        zoom < 5 -> location.countryName
        zoom <= 10 -> location.locality
        zoom < 15 -> location.subLocality
        zoom < 20 -> location.featureName
        else -> location.getAddressLine(0)
    }
}