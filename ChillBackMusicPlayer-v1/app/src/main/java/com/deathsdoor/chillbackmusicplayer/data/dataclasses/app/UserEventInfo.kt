package com.deathsdoor.chillbackmusicplayer.data.dataclasses.app

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.deathsdoor.chillbackmusicplayer.data.Constants
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.daos.CachedEventsDao
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.simplified.EventInfo

@Entity
data class UserEventInfo constructor(
    var booked:Boolean = false,
    @PrimaryKey(autoGenerate = false) var eventID:String,
    @Ignore var data:EventInfo? = null,
    ){
    constructor(booked:Boolean,eventID:String):this(booked,eventID,null)

    fun matchWithData(dao: CachedEventsDao) {
        data = dao.get(eventID) ?:
            Constants.ticketMaster.eventDetails(eventID)?.get(0)?.also {
                dao.insert(it)
            }
    }

}