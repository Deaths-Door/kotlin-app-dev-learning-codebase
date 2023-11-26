package com.deathsdoor.chillbackmusicplayer.data.workmanagers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters


//TODO implement ti
class LocalSongCleanupTask(val context : Context, params : WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}

