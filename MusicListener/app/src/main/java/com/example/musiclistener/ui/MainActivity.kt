package com.example.musiclistener.ui

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log.d
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.musiclistener.CommonFunctions.runDiffCodeDiffTypes
import com.example.musiclistener.CommonFunctions.setLanguage
import com.example.musiclistener.Constants.ALIBYS_LANGUAGES
import com.example.musiclistener.Constants.FB_CURRENT_USER
import com.example.musiclistener.Constants.FB_REALTIME_DB
import com.example.musiclistener.Constants.FB_USER_FOLLOWED_ARTISTS
import com.example.musiclistener.Constants.FB_USER_PLAYLIST
import com.example.musiclistener.Constants.FB_USER_SETTING
import com.example.musiclistener.Constants.FS_USER_PLAYLIST
import com.example.musiclistener.Constants.SETTING_SPRACHE
import com.example.musiclistener.ImageFunctions.generatePlaylistCoverImg
import com.example.musiclistener.Notification
import com.example.musiclistener.R
import com.example.musiclistener.SETTINGS
import com.example.musiclistener.Song
import com.example.musiclistener.adapters.OnClicks
import com.example.musiclistener.appdatabase.AppDataBase
import com.example.musiclistener.databinding.ActivityMainBinding
import com.example.musiclistener.databinding.ActivityMainBinding.inflate
import com.example.musiclistener.music.MusicDownloading.downloadUserSongs
import com.example.musiclistener.music.MusicViewModel
import com.example.musiclistener.music.UserDataFetching.getAllUserPlaylists
import com.example.musiclistener.music.UserDataFetching.matchArtistIDWithData
import com.example.musiclistener.music.UserDataFetching.matchSongIDWithData
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

//TODO UPLOAD APPDATA'S TO FIRESTORE
//TODO change everything to binding
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater).apply {
            vm = ViewModelProvider(this@MainActivity)[MusicViewModel::class.java]
            onClick = OnClicks()
            supportFragManager = supportFragmentManager
            preference = getSharedPreferences(SETTINGS.preferenceName(),Context.MODE_PRIVATE)
            playlistDB = AppDataBase.getDataBase(this@MainActivity).playlistDao()
            artistDB = AppDataBase.getDataBase(this@MainActivity).artistDao()
            localMusicDB = AppDataBase.getDataBase(this@MainActivity).localMusicDao()
        }
        SETTINGS.values().forEach{
            runDiffCodeDiffTypes(it.default,
                {  binding.vm!!.userSettings[it.id] = binding.preference!!.getString(it.name, it.default.toString()) as String },
                {  binding.vm!!.userSettings[it.id] = binding.preference!!.getInt(it.name, it.default as Int)  },
                {  binding.vm!!.userSettings[it.id] = binding.preference!!.getBoolean(it.name, it.default as Boolean) }
            )
        }

        setLanguage(binding.vm!!)
        setContentView(binding.root)
        logIN()
    }
    //if (it.id == SETTINGS.SPRACHE.id) { binding.preference!!.getString(SETTING_SPRACHE.toString(), if (ALIBYS_LANGUAGES.indexOf(Locale.getDefault().language) != -1) Locale.getDefault().language else "en") as Any; return@runDiffCodeDiffTypes  }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_view_pager_host) as NavHostFragment).navController
        when(item.itemId){
            R.id.menu_my_library ->
                when (navController.currentDestination?.id) {
                    R.id.nav_detailed_search     -> navController.navigate(R.id.action_nav_detailed_search_to_nav_my_library)
                    R.id.nav_other_music_sources -> navController.navigate(R.id.action_otherMusicSources_to_nav_my_library)
                    R.id.nav_explore             -> navController.navigate(R.id.action_nav_my_library_to_nav_explore)

                    R.id.nav_playlist_view       -> navController.navigate(R.id.action_nav_playlist_view_to_nav_my_library)
                    R.id.nav_artistProfile       -> navController.navigate(R.id.action_nav_artistProfile_to_nav_my_library)
                    R.id.nav_userProfile         -> navController.navigate(R.id.action_userProfile_to_nav_my_library)
                }
            R.id.menu_detailed_search ->
                when (navController.currentDestination?.id) {
                    R.id.nav_my_library          -> navController.navigate(R.id.action_nav_my_library_to_nav_detailed_search)
                    R.id.nav_other_music_sources -> navController.navigate(R.id.action_otherMusicSources_to_nav_my_library)
                    R.id.nav_explore             -> navController.navigate(R.id.action_nav_detailed_search_to_nav_explore)

                    R.id.nav_playlist_view       -> navController.navigate(R.id.action_nav_playlist_view_to_nav_detailed_search)
                    R.id.nav_artistProfile       -> navController.navigate(R.id.action_nav_artistProfile_to_nav_detailed_search)
                    R.id.nav_userProfile         -> navController.navigate(R.id.action_userProfile_to_nav_detailed_search)
                }
            R.id.menu_other_sources ->
                when(navController.currentDestination?.id){
                    R.id.nav_my_library          -> navController.navigate(R.id.action_nav_my_library_to_nav_other_music_sources)
                    R.id.nav_detailed_search     -> navController.navigate(R.id.action_nav_detailed_search_to_nav_other_music_sources)
                    R.id.nav_explore             -> navController.navigate(R.id.action_nav_explore_to_nav_other_music_sources)

                    R.id.nav_playlist_view       -> navController.navigate(R.id.action_nav_playlist_view_to_nav_other_music_sources)
                    R.id.nav_artistProfile       -> navController.navigate(R.id.action_nav_artistProfile_to_nav_other_music_sources)
                    R.id.nav_userProfile         -> navController.navigate(R.id.action_nav_userProfile_to_nav_other_music_sources)
                }
            R.id.menu_explore ->
                when(navController.currentDestination?.id) {
                    R.id.nav_my_library          -> navController.navigate(R.id.action_nav_my_library_to_nav_explore)
                    R.id.nav_detailed_search     -> navController.navigate(R.id.action_nav_detailed_search_to_nav_explore)
                    R.id.nav_other_music_sources -> navController.navigate(R.id.action_nav_other_music_sources_to_nav_explore)

                    R.id.nav_playlist_view       -> navController.navigate(R.id.action_nav_playlist_view_to_nav_explore)
                    R.id.nav_artistProfile       -> navController.navigate(R.id.action_nav_artistProfile_to_nav_explore)
                    R.id.nav_userProfile         -> navController.navigate(R.id.action_nav_userProfile_to_nav_explore)
                }
        }
        return true
    }
    //Log in
    private val RC_SIGN_IN = 1
    private val RESULT_OK: Int? = null
    private fun logIN(){
        if(FB_CURRENT_USER == null) {
            ActivityCompat.startActivityForResult(this,
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(true)
                    .setAvailableProviders(arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build(), AuthUI.IdpConfig.EmailBuilder().build() )).build(),
                RC_SIGN_IN, Bundle())
        }
        else initAlles()
    }
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if(result.resultCode == RC_SIGN_IN){
            // Successfully signed in
            if (result.resultCode === RESULT_OK){
                if(response!!.isNewUser){
                    FB_USER_SETTING.setValue(binding.vm!!.userSettings)
                    //give default img when new user
                    val updatedInfo = UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvIFhtkP1KVLsieF9iXxl1HN63NJSdNcbzFOkLztujFA&s")).build()
                    FB_CURRENT_USER!!.updateProfile(updatedInfo)
                    FS_USER_PLAYLIST = FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(FB_CURRENT_USER.uid)
                        .collection("playlist")
                    FS_USER_PLAYLIST.document().set(
                        mapOf(
                            "name" to "Liked Songs",
                            "pinned" to true,
                            "songsIDs" to emptyList<String>(),
                        )
                    )
                    FS_USER_PLAYLIST.document().set(
                        mapOf(
                            "name" to "Local Music",
                            "pinned" to true,
                            "songsIDs" to emptyList<String>(),
                            "hidden" to true
                        )
                    )
                    initAlles()
                }
            }
            else {

                // Sign in failed
                if (response == null) return
                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) return
            }
        }
    }
    // einmal check ob songs sind downloaded
    private var checkDownloaded = true
    //Init alles nach den Anmeldung
    @OptIn(DelicateCoroutinesApi::class)
    private fun initAlles(){
        if(binding.vm!!.userSettings[SETTINGS.CURRENT_USER_UUID.id] == "") {
            binding.vm!!.userSettings[SETTINGS.CURRENT_USER_UUID.id] = FB_CURRENT_USER!!.uid
            binding.preference!!.edit().putString(SETTINGS.CURRENT_USER_UUID.id.toString(), FB_CURRENT_USER.uid).apply()
        }


        bottomNav.setOnNavigationItemSelectedListener(this)
        //have offline copy of data
        FirebaseFirestore.getInstance().firestoreSettings = firestoreSettings { isPersistenceEnabled = true}
        FB_REALTIME_DB.keepSynced(true)
        //Setting up exoplayer
        binding.vm!!.exoPlayer = ExoPlayer.Builder(this)
            .setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000)
            .build()
        binding.vm!!.exoPlayer!!.prepare()
        binding.vm!!.exoPlayer!!.play()
        collapsed_exoplayer.player = binding.vm!!.exoPlayer
        Notification(this).createMusicNotification(this,this)

        //Get User Data
        FB_USER_SETTING.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val settings = snapshot.getValue<HashMap<Int,Any>>()
                if(settings != null) {
                    binding.vm!!.userSettings = settings
                    setLanguage(binding.vm!!)
                }
            }
            override fun onCancelled(error: DatabaseError) = Toast.makeText(this@MainActivity,"$${error.message}",Toast.LENGTH_SHORT).show()
        })
        FB_USER_FOLLOWED_ARTISTS.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) = Toast.makeText(this@MainActivity,"$${error.message}",Toast.LENGTH_SHORT).show()
            override fun onDataChange(snapshot: DataSnapshot) {
                GlobalScope.launch {
                    (snapshot.getValue<HashMap<String,String>>())?.values?.forEachIndexed loop@ { index, it ->
                        //alr matched data
                    //    if((binding.vm.allUserArtists.value?.size ?: -1) > index) return@loop

                        val data = matchArtistIDWithData(it)
                        if (data != null && !binding.artistDB!!.artistExists(data.artistID)) binding.artistDB!!.insertArtist(data)
                    }
                }
            }
        })
        binding.artistDB!!.getAllArtist().observe(this){
            //if old user logged out then delete that users data
            if(FB_CURRENT_USER!!.uid != binding.vm!!.userSettings[SETTINGS.CURRENT_USER_UUID.id])
                GlobalScope.launch { binding.playlistDB!!.deleteAll(); binding.artistDB!!.removeAll()}

            binding.vm!!.followedArtists.postValue(it)

        }
        binding.playlistDB!!.getAllPlaylists().observe(this){
            //if old user logged out then delete that users data
            if(FB_CURRENT_USER!!.uid != binding.vm!!.userSettings[SETTINGS.CURRENT_USER_UUID.id])
                GlobalScope.launch { binding.playlistDB!!.deleteAll(); binding.artistDB!!.removeAll()}


            //Get all user data from firestore
            if(it.isEmpty()){
                GlobalScope.launch {
                    var userData = getAllUserPlaylists()
                    //get user data and if error (empty list) then notify user and retry
                    while (userData.isEmpty()){
                        Toast.makeText(this@MainActivity,"Error please check your internet connection",Toast.LENGTH_SHORT).show()
                        userData = getAllUserPlaylists()
                    }
                    userData.forEach { itPlaylist ->
                        val list = arrayListOf<Song>()
                        itPlaylist.songsIDs.forEach { itSongID ->
                            //Match and add data
                            val songData = matchSongIDWithData(itSongID)
                            if(songData != null) list.add(songData)
                        }
                        itPlaylist.songs = list
                        binding.playlistDB!!.insertPlaylist(itPlaylist)
                    }
                }
            }

            if(checkDownloaded) { downloadUserSongs(it,this,binding.vm!!); checkDownloaded = false }
            binding.vm!!.userPlaylist.postValue(generatePlaylistCoverImg(it).sortedBy { playlists -> !playlists.pinned })
        }
        FB_USER_PLAYLIST.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) = Toast.makeText(this@MainActivity,"$${error.message}",Toast.LENGTH_SHORT).show()
            override fun onDataChange(snapshot: DataSnapshot) {
            }
        })
    }
}