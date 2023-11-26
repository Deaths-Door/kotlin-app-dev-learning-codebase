package com.deathsdoor.chillbackmusicplayer.data.appdatabase.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.simplified.EventInfo
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.UserEventInfo

@Dao
interface CachedEventsDao {
    @Insert
    fun insert(data: EventInfo)

    @Query("SELECT * from EventInfo WHERE eventID = :eventID")
    fun get(eventID: String):EventInfo?
}