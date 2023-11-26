package com.deathsdoor.chillbackmusicplayer.data.extensions

import android.content.Context
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.MediaID
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.AudioFilesDetect
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.CoroutineHelper
import com.deathsdoor.chillbackmusicplayer.data.music.JAudioTagger
import java.io.File

object UserDataFetching {
    @Deprecated("for some reason it causes an error but using LocalMusicView Line 52 doesnt")
    fun Context.matchWithData(data:List<MediaID>): ArrayList<Song> {
        val audioDetector = AudioFilesDetect(this)
        val jAudioTagger = JAudioTagger(null)
        val array = arrayListOf<Song>()
        data.forEach {

            audioDetector.fileName = it.mediaID
            audioDetector.extractExternalFileMediaColumns { path ->
                if(path == null) TODO("Either file doesnt exist or its from api ")

                jAudioTagger.file = File(path[0])
                array.add(
                    Song(
                        jAudioTagger.title,
                        jAudioTagger.artist,
                        jAudioTagger.album,
                        jAudioTagger.genre,
                        jAudioTagger.albumArtist,
                        //TODO detect lyrics file
                        "https://www.google.com",
                        path[0],
                        "",
                        jAudioTagger.file!!.name
                    )
                )
            }
        }
        return array
    }
    suspend fun Context.getMatchData(): List<Song> {
        val jAudioTagger = JAudioTagger(null)
        val audioDetector by lazy { AudioFilesDetect(this) }
        var data:List<Song> = arrayListOf()
        audioDetector.extractExternalFileMediaColumns {
            if(it == null) return@extractExternalFileMediaColumns
            //      array.addAll(requireContext().matchWithData(it.map { MediaID(it) }))
            data = it.map {path ->
                jAudioTagger.file = File(path)
                //TODO detect lyrics file
                Song(
                    jAudioTagger.title,
                    jAudioTagger.artist,
                    jAudioTagger.album,
                    jAudioTagger.genre,
                    jAudioTagger.albumArtist,
                    "TODO detect lyrics file",
                    path,
                    "",
                    jAudioTagger.file!!.name
                ) }
        }

        return data
    }
}