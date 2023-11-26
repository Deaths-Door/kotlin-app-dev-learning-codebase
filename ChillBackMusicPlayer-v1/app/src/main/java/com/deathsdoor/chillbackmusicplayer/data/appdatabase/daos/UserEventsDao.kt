package com.deathsdoor.chillbackmusicplayer.data.appdatabase.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.UserEventInfo

@Dao
interface UserEventsDao {
    @Insert
    fun insert(data: UserEventInfo)

    @Query("DELETE FROM UserEventInfo WHERE eventID = :eventID")
    fun delete(eventID: String)

    @Query("SELECT * from UserEventInfo")
    fun all() : List<UserEventInfo>?
}