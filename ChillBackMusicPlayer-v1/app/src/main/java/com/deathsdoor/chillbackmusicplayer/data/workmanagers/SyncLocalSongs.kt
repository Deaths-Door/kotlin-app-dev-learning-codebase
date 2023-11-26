package com.deathsdoor.chillbackmusicplayer.data.workmanagers

import android.content.Context
import androidx.work.*
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.AppDataBase
import java.util.concurrent.TimeUnit

//TODO use this and make it do something
class SyncLocalSongs(val context : Context, params : WorkerParameters) : Worker(context, params) {
    companion object{
        fun create(): PeriodicWorkRequest
            = PeriodicWorkRequestBuilder<SyncLocalSongs>(1, TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build())
            .build()
    }
    override fun doWork(): Result {
        //TODO sync like songs and
        //dao.getAllLikedSongs()?.forEach {
          /*  Contants.FB_LIKED_SONGS.child(it.mediaID).addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        //Update info in db and which is newer
                        val data = (snapshot.value as Song)
                        data.timeStamp.compareTimeStamps("")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO show notification error syncing data
                }
            })*/
       // }
        return Result.success()
    }
}