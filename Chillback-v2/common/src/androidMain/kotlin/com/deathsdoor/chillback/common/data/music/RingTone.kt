package com.deathsdoor.chillback.common.data.music

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import com.deathsdoor.astroplayer.core.dataclasses.MediaItem

internal fun RingTone.setAs(context : Context, mediaItem : MediaItem) {
    val values = ContentValues().apply {
        put(MediaStore.MediaColumns.DATA, mediaItem.media.toString())
        put(MediaStore.MediaColumns.TITLE, mediaItem.metadata.title)
        put(MediaStore.Audio.Media.IS_RINGTONE, true)
        put(MediaStore.Audio.Media.IS_NOTIFICATION, false)
        put(MediaStore.Audio.Media.IS_ALARM, false)
        put(MediaStore.Audio.Media.IS_MUSIC, false)
    }
    context.contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)
}