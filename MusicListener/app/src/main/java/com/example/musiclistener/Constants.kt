package com.example.musiclistener

import com.algolia.search.saas.Client
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

object Constants {
    const val MUSIC_DATA_DB = "user_data.db"

    const val SETTING_SPRACHE = 10

    const val USE_USER_PLAYLIST = 1
    const val USE_LOCAL_MUSIC = 2
    const val USE_CURRENT_PLAYLIST = 3
    const val USE_CURRENT_ARTIST = 4

    private const val ALOGIA_APPLICATION_ID = "ED7AHDYTMS"
    private const val ALOGIA_API_KEY = "57b25a0583397c5a7ad02f0cc556ee03"
    private val ALOGIA_CLIENT = Client(ALOGIA_APPLICATION_ID,ALOGIA_API_KEY)
    val ALOGIA_SONG_INDEX = ALOGIA_CLIENT.getIndex("songs")

    val ALIBYS_LANGUAGES = arrayListOf("en","de","fr","tr")


    val FB_CURRENT_USER: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    val FB_REALTIME_DB = FirebaseDatabase.getInstance().reference

    @Deprecated("New action is in development and decide whether should use firestore or realtime db")
    var FS_USER_PLAYLIST = FirebaseFirestore.getInstance().collection("me_user")
    val FB_USER_PLAYLIST = FB_REALTIME_DB.child("playlists/${FB_CURRENT_USER?.uid}")

    val FS_ALL_SONGS = FirebaseFirestore.getInstance().collection("songs")
    val FS_ALL_ARTIST = FirebaseFirestore.getInstance().collection("artist")
    val FB_STORAGE = FirebaseStorage.getInstance().reference.child("Data")
    val FB_USER_SETTING = FB_REALTIME_DB.child("settings/${FB_CURRENT_USER?.uid}")
    val FB_USER_FOLLOWED_ARTISTS = FB_REALTIME_DB.child("followed_artist/${FB_CURRENT_USER?.uid}")
}

enum class SETTINGS(val id:Int,val default:Any) {
    CROSS_FADING                        (3, -1),
    NORMALIZE_TRACK                     (1, true),
    SKIP_SILENCE                        (2, true),
    MOBILE_DATA_STREAMING_VIDEOS        (4, false),
    MOBILE_DATA_STREAMING_MUSIC         (5, false),
    SHOW_LOCAL_MUSIC_FILES              (6, false),
    DOWNLOAD_VIDEO_FILES                (7, false),
    MOBILE_DATA_DOWNLOADING_MUSIC_FILES (8, true),
    MOBILE_DATA_DOWNLOADING_VIDEO_FILES (9, false),
    MOBILE_DATA_DOWNLOADING_LYRIC_FILES (10,true),
    SPRACHE                             (0, "en"),
    CURRENT_USER_UUID                   (11,""),
    SPOTIFY_USERNAME                    (12,""),
    SPOTIFY_PASSWORD                    (13,""),
    YOUTUBE_USERNAME                    (14,""),
    YOUTUBE_PASSWORD                    (15,""),
    YOUTUBE_MUSIC_USERNAME              (16,""),
    YOUTUBE_MUSIC_PASSWORD              (17,""),
    APPLE_MUSIC_USERNAME                (18,""),
    APPLE_MUSIC_PASSWORD                (19,""),
    AMAZON_MUSIC_USERNAME               (20,""),
    AMAZON_MUSIC_PASSWORD               (21,""),
    TIDAL_USERNAME                      (22,""),
    TIDAL_PASSWORD                      (23,"");
    companion object { @JvmStatic fun preferenceName(): String = "user_settings" }
}

enum class WEBPAGE(val id:Int ,val url:String){
    SPOTIFY         (10,"https://open.spotify.com/"),
    YOUTUBE         (15,"https://youtube.com/"),
    YOUTUBE_MUSIC   (24,"https://music.youtube.com/"),
    APPLE_MUSIC     (34,"https://www.apple.com/apple-music/"),
    AMAZON_MUSIC    (41,"https://music.amazon.com/"),
    TIDAL           (50,"https://tidal.com/")
}

enum class CONNECTION(val id:Int){
    NONE   (23),
    MOBILE (13),
    WLAN   (54)
}

enum class SORT(val id:Int){
    A_Z(23),
    Z_A(54),
    MOST_PLAYED(95),
    MOST_RECENT(36),
    RECENTLY_PLAYED(75),
    RECENTLY_ADDED(21);
}

enum class UNTERSTÜTZESSPRACHE(val shortForm:String){
    ENGLISH ("en"),
    FRENCH  ("fr"),
    GERMAN  ("de"),
    TURKISH ("tr");
    companion object{
        //Full language name written or short form written
        fun spracheSupported(s:String): Boolean {
            UNTERSTÜTZESSPRACHE.values().forEach { if(it.name == s.toUpperCase() || it.shortForm == s) return true }
            return false
        }
    }
}