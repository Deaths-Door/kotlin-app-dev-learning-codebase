package com.example.exoplayer

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){
    private val songURL = "https://firebasestorage.googleapis.com/v0/b/music-player-b1e6d.appspot.com/o/songs%2FThe%20Chainsmokers%20-%20Closer%20(Lyrics)%20ft.%20Halsey.mp3?alt=media&token=29d869f0-6a59-4e19-b3f8-b4597306db0a"
    private val songURL2 = "https://firebasestorage.googleapis.com/v0/b/music-player-b1e6d.appspot.com/o/songs%2FThe%20Chainsmokers%20-%20All%20We%20Know%20(Lyrics)%20ft.%20Phoebe%20Ryan.mp3?alt=media&token=f97846e5-02d8-4c2e-8e48-c1c7035c9fbe"
    private var exoPlayer: ExoPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exoPlayer = ExoPlayer.Builder(this).build()
        idExoPlayerView.player = exoPlayer
        val mediaItem = MediaItem.fromUri(Uri.parse(songURL))
        val mt = MediaMetadata.Builder().setTitle("Title").setArtist("artsits").setAlbumTitle("album artist").setAlbumArtist("album aritsts").build()
        val mi = MediaItem.Builder().setMediaMetadata(mt).setUri(Uri.parse(songURL))
            .build()
        val t = MediaItem.fromUri(Uri.parse(songURL2))
        exoPlayer!!.addMediaItem(mi)
        exoPlayer!!.prepare()
        exoPlayer!!.play()
    }
    override fun onDestroy() {
        super.onDestroy()
        exoPlayer!!.release()
    }
}