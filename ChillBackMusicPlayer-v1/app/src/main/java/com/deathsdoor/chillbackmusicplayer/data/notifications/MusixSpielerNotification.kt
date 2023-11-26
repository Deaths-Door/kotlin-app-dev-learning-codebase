package com.deathsdoor.chillbackmusicplayer.data.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import com.deathsdoor.chillbackmusicplayer.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class MusixSpielerNotification(private val context: Context, private val notificationID:Int, private val channelID:String): Notification(context,notificationID, channelID) {
    private val defaultMediaDescriptionAdapter = object : PlayerNotificationManager.MediaDescriptionAdapter {
            override fun createCurrentContentIntent(player: Player): PendingIntent? = null
            override fun getCurrentContentTitle(player: Player): CharSequence = player.currentMediaItem?.mediaMetadata?.title ?: "Play some songs"
            override fun getCurrentContentText(player: Player): CharSequence = player.currentMediaItem?.mediaMetadata?.artist ?: ""
            //TODO find method to get bitmap that doesnt check for a file instead makes it from the uri
            override fun getCurrentLargeIcon(player: Player, callback: PlayerNotificationManager.BitmapCallback): Bitmap? = null
        }

    fun createPlayerNotification(exoPlayer: ExoPlayer?, importance: Int = NotificationManager.IMPORTANCE_MAX, mediaDescriptionAdapter: PlayerNotificationManager.MediaDescriptionAdapter = defaultMediaDescriptionAdapter) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel(importance)
        PlayerNotificationManager.Builder(context, notificationID, channelID)
            .setMediaDescriptionAdapter(mediaDescriptionAdapter)
            .setNotificationListener(object : PlayerNotificationManager.NotificationListener {})
            .build()
            .also { it.setPlayer(exoPlayer) }
            .also { it.setSmallIcon(R.drawable.ic_launcher_background) }
    }
}