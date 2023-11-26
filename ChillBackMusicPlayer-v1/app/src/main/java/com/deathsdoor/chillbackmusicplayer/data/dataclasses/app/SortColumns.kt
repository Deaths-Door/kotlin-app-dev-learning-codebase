package com.deathsdoor.chillbackmusicplayer.data.dataclasses.app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Deprecated("use different way to sort it")
@Parcelize
data class SortColumns(
    val byArtist: Boolean = true,
    val byAlbumArtist:Boolean = true,
    val byTitle: Boolean = true,
    val byAlbum: Boolean = true,
    val byYear: Boolean = true,
    val byGenre: Boolean = true) : Parcelable