package com.deathsdoor.chillback.common.data.music

import com.deathsdoor.astroplayer.core.dataclasses.MediaItem
import com.deathsdoor.astroplayer.core.dataclasses.MediaMetadata
import com.deathsdoor.chillback.common.data.database.AdditionalLocalSongInfo
import com.deathsdoor.chillback.common.data.database.Caching
import com.deathsdoor.uri.URI
import com.deathsdoor.uuid.UUID

internal suspend fun MutableList<String>.matchMusicFilesLocalSongInfo() : List<MediaItem> {
    val info = Caching.localSongAdditionalInfoEntries.filter { it.isHidden == false }
    val ids = info.associateBy { it.path }
    return this@matchMusicFilesLocalSongInfo.map { path ->
        val extractor = MusicMetadataExtractor(path)
        val it = ids[path]
        return@map MediaItem(
            id = it?.id ?: run {
                val x = AdditionalLocalSongInfo(
                    id = UUID.random(),
                    path = path
                )
                Caching.cacheLocalSongAdditionalInfo(x)
                x.id
            },
            media = URI.parse(path),
            metadata = MediaMetadata(
                trackLength = extractor.trackLength,
                artwork = extractor.artwork,
                title = extractor.name,
                albumTitle = extractor.album,
                albumArtist = extractor.albumArtists,
                artist = extractor.artists,
                isLiked = it?.isLiked ?: false
            )
        )
    }
} 