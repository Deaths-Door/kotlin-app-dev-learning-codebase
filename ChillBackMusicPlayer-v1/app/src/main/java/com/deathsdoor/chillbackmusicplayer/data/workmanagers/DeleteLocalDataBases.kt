package com.deathsdoor.chillbackmusicplayer.data.workmanagers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

//TODO implement it
class DeleteLocalDataBases(val context : Context, params : WorkerParameters) : Worker(context, params) {
    //TODO upload all local database data to firebase to the last firebase user and delete it
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}