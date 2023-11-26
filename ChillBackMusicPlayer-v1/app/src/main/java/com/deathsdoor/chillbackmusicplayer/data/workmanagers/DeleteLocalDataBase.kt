package com.deathsdoor.chillbackmusicplayer.data.workmanagers

import android.content.Context
import androidx.work.*
import com.deathsdoor.chillbackmusicplayer.data.Constants
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.AppDataBase
import com.deathsdoor.chillbackmusicplayer.data.firebase.FireAuthHelper
import com.deathsdoor.ui_core.public.PreferenceExtensions.getPreferenceValue
import com.deathsdoor.ui_core.public.PreferenceExtensions.sharedPreference
import java.util.concurrent.TimeUnit

//TODO use create to start it
//TODO add any new daos to the thing
//Used when the currentUSER UID doesnt match the one that data is stored with
class DeleteLocalDataBases(val context : Context, params : WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val appDataBase = AppDataBase.dataBase(context)
        if(FireAuthHelper.currentUser?.uid == null) {
            appDataBase.userAlbumDao().deleteAll()
            return Result.success()
        }
        val preference = context.sharedPreference(Constants.SETTING_PREFERENCE)
        val value = preference?.getPreferenceValue(FireAuthHelper.currentUser.uid,"")
        if(FireAuthHelper.currentUser.uid != (value ?: "")) {
            appDataBase.userAlbumDao().deleteAll()
            appDataBase.likedSongDao().deleteAll()
        }
        return Result.success()
    }
    companion object {
        fun create(context: Context){
            val work = PeriodicWorkRequestBuilder<DeleteLocalDataBases>(1, TimeUnit.DAYS)
                .setInitialDelay(1,TimeUnit.HOURS)
                .build()
            WorkManager.getInstance(context).enqueue(work)
        }
    }
}