package com.deathsdoor.chillback.common.data.database

import androidx.compose.runtime.Stable
import com.deathsdoor.uuid.UUID
import kotlinx.serialization.Serializable

@Serializable
internal class AdditionalLocalSongInfo(
    val id : UUID,
    val path : String,
    var isHidden : Boolean? = null,
    var isLiked : Boolean? = null,
    val albumRelations : MutableList<UUID> = mutableListOf()
)

