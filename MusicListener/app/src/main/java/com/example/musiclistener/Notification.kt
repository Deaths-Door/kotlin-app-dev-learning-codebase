package com.example.musiclistener

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.example.musiclistener.music.MusicViewModel
import com.example.musiclistener.ui.MainActivity
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class Notification(private val context: Context) {
    private var notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val musicNotificationID: Int = 1
    private val musicChannelId = "notification_exoplayer"
    private val progressChannelID = "notification_progress"
    private val progressNotificationID: Int = 2
    fun createMusicNotification(context: Context, activity: MainActivity){
        val vm by lazy { ViewModelProvider(activity)[MusicViewModel::class.java]}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(musicChannelId,musicChannelId,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification =
            PlayerNotificationManager.Builder(
                context,
                musicNotificationID, musicChannelId)
                .setMediaDescriptionAdapter(object:PlayerNotificationManager.MediaDescriptionAdapter{
                    override fun createCurrentContentIntent(player: Player): PendingIntent? = null
                    override fun getCurrentContentTitle(player: Player): CharSequence =
                        if(player.currentMediaItem == null) "Play constants song"
                        else player.currentMediaItem!!.mediaMetadata.title.toString()
                    override fun getCurrentContentText(player: Player): CharSequence =
                        if(player.currentMediaItem == null) ""
                        else player.currentMediaItem!!.mediaMetadata.artist.toString()
                    //TODO give bogus error
                    override fun getCurrentLargeIcon(player: Player, callback: PlayerNotificationManager.BitmapCallback): Bitmap? {
                        return null
                    //if(player.currentMediaItem == null) Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888)
                     //   else bitmapFromUri(context,player.currentMediaItem!!.mediaMetadata.artworkUri)
                    }
                })
                .setNotificationListener(object :PlayerNotificationManager.NotificationListener{})
                .build()

        notification.setPlayer(vm.exoPlayer)
        notification.setSmallIcon(R.drawable.ic_splash_screen_logo)

    }
    fun cancelMusicNotification() = notificationManager.cancel(musicNotificationID)
    fun createProgressNotification(){
        val notification =
            NotificationCompat.Builder(context, progressChannelID)
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle(context.getString(R.string.notification_offline_support_msg))
                .setContentText(context.getString(R.string.downloading))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(100, 0, true)
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(progressChannelID,progressChannelID,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(progressNotificationID,notification.build())
    }
    fun cancelProgressNotification(){
        val notification =
            NotificationCompat.Builder(context, progressChannelID)
                .setSmallIcon(R.drawable.ic_download)
                .setContentText(context.getString(R.string.download_complete))
                .setProgress(0, 0, false)
                .setOngoing(false)
        notificationManager.notify(progressNotificationID, notification.build())
    }
}
