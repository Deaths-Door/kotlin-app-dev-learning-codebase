package com.deathsdoor.chillbackmusicplayer.test

import androidx.lifecycle.lifecycleScope
import com.deathsdoor.chillbackmusicplayer.data.extensions.debugLog
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.CoroutineHelper
import com.deathsdoor.chillbackmusicplayer.data.api.MusixAbgleich
import com.deathsdoor.chillbackmusicplayer.ui.activity.MainActivity
import kotlinx.coroutines.launch

//TODO doesnt work
val testMuxMatch = MusixAbgleich("96868e3713c6d6ced65dd79a1196771c")
fun MainActivity.attempt() {
   // val answer = testMuxMatch.getTopArtistTest(1,5,"it")
   // "MusicMatchResponse".debugLog("Answer Array: $answer")
   // val answer1 = testMuxMatch.getTopArtist(1,5,"it")
   // "MusicMatchResponse".debugLog("Answer Single: $answer1")

    lifecycleScope.launch {
        CoroutineHelper.onBackgroundThread {
            val a = testMuxMatch.getTopArtistTest(1,5,"it")
            "MusicMatchResponse".debugLog("Answer Array: $a")
            val b = testMuxMatch.getTopArtist(1,5,"it")
            "MusicMatchResponse".debugLog("Answer Single: $b")
        }
    }
  //  val answer1 = testMuxMatch.getTopArtistTest(1,5,"it")
   // "MusicMatchResponse".debugLog("Answer Array: $answer1")
}