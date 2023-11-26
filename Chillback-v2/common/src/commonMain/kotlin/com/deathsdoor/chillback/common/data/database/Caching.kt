package com.deathsdoor.chillback.common.data.database

import com.deathsdoor.musicmatch.dataclasses.MusixLyrics
import com.deathsdoor.ticketmaster.dataclasses.TicketEvent
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal object Caching {
    private val preference = Settings()

    var localSongAdditionalInfoEntries : List<AdditionalLocalSongInfo>
        get() = Json.decodeFromString(preference["additionalLocalSongInfo", "[]"])
        private set(value) { preference["additionalLocalSongInfo"] = Json.encodeToString(value) }

    var ticketMasterEventsEntries : List<TicketEvent>
        get() = Json.decodeFromString(preference["ticketEvent", "[]"])
        private set(value) { preference["ticketEvent"] = Json.encodeToString(value) }

    var musixMatchLyrics : List<MusixLyrics>
        get() = Json.decodeFromString(preference["musixMatchLyrics", "[]"])
        private set(value) { preference["musixMatchLyrics"] = Json.encodeToString(value) }

    suspend fun cacheLocalSongAdditionalInfo(vararg data : AdditionalLocalSongInfo){
        val entries = localSongAdditionalInfoEntries.toMutableList()

        val existingIDS = entries.associateBy { it.id }

        data.forEach { info ->
            val result = existingIDS[info.id]
            if(result == null) entries.add(info)
            else with(entries[entries.indexOf(info)]){
                isHidden = info.isHidden
                isLiked = info.isLiked
                albumRelations.addAll(info.albumRelations)
            }
        }

        localSongAdditionalInfoEntries = entries
    }

    suspend fun cacheTicketMasterEvents(vararg data : TicketEvent){
        val existingEvents = ticketMasterEventsEntries

        ticketMasterEventsEntries = existingEvents.map { existingEvent -> // Map over existingEvents and replace events that have a matching event in data and are not equal
            val matchingEvent = data.find { it == existingEvent } // Find a matching event in data based on custom equality check
            matchingEvent ?: existingEvent // If a matching event is found, replace the existingEvent; otherwise, keep it
        } + data.filterNot { existingEvents.contains(it) } // Add events from data that are not present in existingEvents
    }
}