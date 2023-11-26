package com.deathsdoor.chillback.common.data.firebase

import com.deathsdoor.chillback.common.data.database.AdditionalLocalSongInfo
import com.deathsdoor.chillback.common.data.database.Caching
import com.deathsdoor.chillback.common.data.music.Album
import com.deathsdoor.chillback.common.data.settings.Settings
import com.deathsdoor.chillback.common.ui.theme.Theme
import com.deathsdoor.uri.isSourceFile
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.collectLatest

private val userPlaylistPath get() = "${Firebase.auth.currentUser!!.uid}.playlists"

internal suspend fun Firebase.currentTheme(onThemeChange  : (Theme.Values) -> Unit) = Firebase.database.reference(Settings.currentTheme).valueEvents.collectLatest {
    onThemeChange(it.value())
}

internal suspend fun Firebase.userPlaylists(action : (List<Album>) -> Unit) = Firebase.firestore.collection(userPlaylistPath).snapshots.collectLatest { snapshot ->
    //Map Local Songs correctly as well to the album
    action(snapshot.documents.map { it.data(Album.serializer()) })
}

//TODO on upload ignore id field as doc name is album id
private suspend fun Firebase.insertPlaylist(album: Album) = Firebase.firestore.collection(userPlaylistPath).document(album.id.toString()).set(album)

//Separating Device and Api songs , so device songs are stored on local database instead of firebase
private fun Firebase.separateMediaSources(album: Album) = album.songs.partition { it.media.isSourceFile }

internal suspend fun Firebase.insertToAppropriateStorage(album: Album){
    val (device,api) = separateMediaSources(album)

    album.songs.clear()
    album.songs.addAll(api)

    insertPlaylist(album)

    Caching.cacheLocalSongAdditionalInfo(
        *device.map {
            AdditionalLocalSongInfo(
                id = it.id,
                path = it.metadata.toString(),
                isLiked = it.metadata.isLiked,
                albumRelations = mutableListOf(album.id)
            )
        }.toTypedArray()
    )
}