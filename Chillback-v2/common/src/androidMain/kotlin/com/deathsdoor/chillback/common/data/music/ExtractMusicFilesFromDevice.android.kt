package com.deathsdoor.chillback.common.data.music

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.deathsdoor.chillback.common.data.permissions.Permissions.canReadExternalStorage
import com.deathsdoor.chillback.common.ui.providers.GlobalViewModel

internal suspend fun extractMusicFilesFromDevice(context : Context): MutableList<String> {
    val filePaths = mutableListOf<String>()
    val search = MediaStore.Audio.Media.DATA
    context.contentResolver
        .query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(search),
            null,
            null,
            null
        )?.use {
            val index = it.getColumnIndexOrThrow(search)
            while (it.moveToNext()) {
                filePaths.add(it.getString(index))
            }
        }
    return filePaths
}

@Composable
internal actual fun ExtractMatchMusicFiles(){
    val viewmodel  = GlobalViewModel.current

    if(viewmodel.songsOnUserDevice.value.isNotEmpty()) return

    val context = LocalContext.current

    LaunchedEffect(Unit){
        if(!canReadExternalStorage(context) || viewmodel.isSongsOnUserDeviceDetectionRunning) return@LaunchedEffect

        viewmodel.isSongsOnUserDeviceDetectionRunning = true
        viewmodel.songsOnUserDevice.value = extractMusicFilesFromDevice(context).matchMusicFilesLocalSongInfo().toMutableList()
        viewmodel.isSongsOnUserDeviceDetectionRunning = false
        Log.d("MUSIC","${viewmodel.songsOnUserDevice.value}")
    }    
}
