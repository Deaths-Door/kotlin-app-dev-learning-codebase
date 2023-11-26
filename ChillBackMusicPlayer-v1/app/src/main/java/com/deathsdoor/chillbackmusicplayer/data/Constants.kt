package com.deathsdoor.chillbackmusicplayer.data

import android.os.Environment
import android.os.Looper
import android.os.Parcelable
import com.deathsdoor.chillbackmusicplayer.data.api.TicketMaster
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.SortColumns
import com.deathsdoor.chillbackmusicplayer.data.firebase.FireAuthHelper
import kotlinx.parcelize.Parcelize
import org.jmusixmatch.MusixMatch
import kotlin.random.Random

val randomNumber : Int get() = Random(System.currentTimeMillis()).nextInt()
val isMainThread get() = Looper.myLooper() == Looper.getMainLooper()

object Constants {

    @Parcelize
    enum class ORIENTATION : Parcelable {
        VERTICAL,
        GRID
    }

    @Deprecated("why the hell do i need this")
    enum class FILE_EXTENSIONS(val extension:String){
        MP3(".mp3")
    }

    const val SETTING_PREFERENCE = "setting_preference"

    enum class SETTING(val defaultValue:Any?){
        FIREBASE_AUTH_CURRENT_USER(FireAuthHelper.currentUser?.uid),
        MIN_DOWNLOADED_FILES_CLASSIFY_ALBUM_AS_DOWNLOADED(75),
        FILE_EXPORT_DIRECTORY(Environment.getExternalStorageDirectory()),
        @Deprecated("use different way to sort it")
        LOCAL_SONG_SORT_COLUMNS_VERTICAL(SortColumns()),
        @Deprecated("use different way to sort it")
        LOCAL_SONG_SORT_COLUMNS_HORIZONTAL(SortColumns())
    }

    const val TICKET_MASTER_API_KEY= "cGS0uFE0Dz6sgt2tlmpwWX6wuOXTDWEi"

    val ticketMaster = TicketMaster(TICKET_MASTER_API_KEY)
    val musicMatch = MusixMatch("96868e3713c6d6ced65dd79a1196771c")

}