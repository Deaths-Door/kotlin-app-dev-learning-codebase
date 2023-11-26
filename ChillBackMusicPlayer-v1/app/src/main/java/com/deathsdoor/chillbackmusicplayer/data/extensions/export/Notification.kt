package com.deathsdoor.chillbackmusicplayer.data.extensions.export

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

open class Notification(private val context:Context, private val notificationID:Int, private val channelID:String) {
    private fun notificationManger(): NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun cancelNotification() = notificationManger().cancel(notificationID)
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(importance: Int = NotificationManager.IMPORTANCE_MIN)
            = notificationManger().createNotificationChannel(NotificationChannel(channelID,channelID,importance))
    fun createNotification(){
        //TODO implement it
    }
}

