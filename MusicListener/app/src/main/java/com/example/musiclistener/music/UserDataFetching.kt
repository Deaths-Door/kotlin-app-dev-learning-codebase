package com.example.musiclistener.music

import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import com.example.musiclistener.*
import com.example.musiclistener.Constants.FS_ALL_ARTIST
import com.example.musiclistener.Constants.FS_ALL_SONGS
import com.example.musiclistener.Constants.FS_USER_PLAYLIST
import kotlinx.coroutines.tasks.await
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.musiclistener.CommonFunctions.mapToList
import com.example.musiclistener.appdatabase.LocalMusicDao

object UserDataFetching {
    suspend fun matchSongIDWithData(songID:String): Song? = FS_ALL_SONGS.document(songID).get().await().toObject(Song::class.java)
    suspend fun matchArtistIDWithData(artistId:String) = FS_ALL_ARTIST.document(artistId).get().await().toObject(Artist::class.java)
    suspend fun getAllUserPlaylists():List<Playlists> = try{ FS_USER_PLAYLIST.get().await().toObjects(Playlists::class.java) } catch (e:Exception){ emptyList() }
    fun Context.detectLocalMusicFiles() : List<LocalSong>{
        val proj = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            arrayOf(
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ARTIST,
                MediaStore.Audio.Media.GENRE
            )
        else arrayOf(
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ALBUM)

        val audioCursor: Cursor? = contentResolver
            .query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null, null
            )
        val data = arrayListOf<LocalSong>()
        if (audioCursor != null) {
            if (audioCursor.moveToFirst()) {
                do {
                    data.add(
                        LocalSong(
                            title = audioCursor.getString(audioCursor.getColumnIndexOrThrow(
                                MediaStore.Audio.Media.TITLE)),
                            artist = audioCursor.getString(audioCursor.getColumnIndexOrThrow(
                                MediaStore.Audio.Media.ARTIST)),
                            album = audioCursor.getString(audioCursor.getColumnIndexOrThrow(
                                MediaStore.Audio.Media.ALBUM)),
                            mediaID = audioCursor.getString(audioCursor.getColumnIndexOrThrow(
                                MediaStore.Audio.Media.DISPLAY_NAME)),
                            genre = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) audioCursor.getString(audioCursor.getColumnIndexOrThrow(
                                MediaStore.Audio.Media.ALBUM_ARTIST)) else "Unknown",
                            albumArtist = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) audioCursor.getString(audioCursor.getColumnIndexOrThrow(
                                MediaStore.Audio.Media.GENRE))  else "Unknown"
                        )
                    )
                } while (audioCursor.moveToNext())
            }
        }
        audioCursor?.close()
        return data
    }
    fun List<LocalSong>.changeAlteredData(localMusicDB: LocalMusicDao): List<LocalSong> {
        this.forEach {
            val t =  localMusicDB.getMusic(it.mediaID)
        }
        return this
    }
    fun List<Song>.sortIntoCategories(): List<Playlists> {
        val data = HashMap<String,Playlists>()
        this.forEach {
            if(data[it.artist] == null) data[it.artist] = Playlists(name = it.artist)
            data[it.artist]!!.addSong(it)
            data[it.artist]!!.mainArtists = data[it.artist]!!.songsIDs.size.toString()
        }
        return data.mapToList() as List<Playlists>
    }
}