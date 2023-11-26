package com.deathsdoor.chillbackmusicplayer.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.Constants
import com.deathsdoor.chillbackmusicplayer.data.Constants.ticketMaster
import com.deathsdoor.chillbackmusicplayer.data.api.TicketMaster
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.AppDataBase
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.MediaID
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.showToast
import com.deathsdoor.chillbackmusicplayer.data.extensions.Image.loadImage
import com.deathsdoor.chillbackmusicplayer.data.extensions.UserDataFetching.getMatchData
import com.deathsdoor.chillbackmusicplayer.data.extensions.debugLog
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.ClickListeners.onClick
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.CoroutineHelper
import com.deathsdoor.chillbackmusicplayer.data.firebase.FireAuthHelper.Companion.currentUser
import com.deathsdoor.chillbackmusicplayer.data.firebase.FireAuthHelper.Companion.firebaseAuth
import com.deathsdoor.chillbackmusicplayer.data.music.MusixSpiele
import com.deathsdoor.chillbackmusicplayer.data.music.PlayerNotification
import com.deathsdoor.chillbackmusicplayer.data.permissions.LocationPermissionHandler
import com.deathsdoor.chillbackmusicplayer.data.permissions.PermissionHandler
import com.deathsdoor.chillbackmusicplayer.data.permissions.StoragePermissionsHandler
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.data.workmanagers.DeleteLocalDataBases
import com.deathsdoor.chillbackmusicplayer.databinding.ActivityMainBinding
import com.deathsdoor.chillbackmusicplayer.databinding.ActivityMainBinding.inflate
import com.deathsdoor.chillbackmusicplayer.ui.bottomsheets.ExpandedExoplayer
import com.deathsdoor.ui_core.public.PreferenceExtensions.getPreferenceValue
import com.deathsdoor.ui_core.public.PreferenceExtensions.sharedPreference
import com.google.firebase.auth.FirebaseAuth
import com.like.LikeButton
import it.sephiroth.android.library.bottomnavigation.BottomNavigation
import kotlinx.coroutines.launch


//TODO deprecect album / song recycler view layouts as its the same thing
//TODO add swiping to recycler view
//TODO store URIs in sources better

//TODO company NovaTune
//TODO handle multiple instances of login screen
//TODO put everything in a function so that till user isnt logged in it wont do anything maybe to this
@Deprecated("trying to replace it using composable but will take long")
class MainActivity : AppCompatActivity(){
    private lateinit var binding : ActivityMainBinding
    private val mainViewmodel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val likedSongsDao by lazy { AppDataBase.dataBase(this).likedSongDao() }
    private val musicNotification by lazy { PlayerNotification(this,1,"notification_exoplayer") }
    private val permissionLocation by lazy { LocationPermissionHandler(this, accessCoarseLocation = true, accessFineLocation = true) }
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = inflate(layoutInflater)
        setContentView(binding.root)

        //TODO doesnt work
        authStateListener = FirebaseAuth.AuthStateListener {
            if(mainViewmodel.loginPageShown) return@AuthStateListener

            currentUser?.let { DeleteLocalDataBases.create(this) }

            if(currentUser == null || sharedPreference(Constants.SETTING_PREFERENCE)?.getPreferenceValue(Constants.SETTING.FIREBASE_AUTH_CURRENT_USER.name,"") != currentUser.uid){
//                LoginPage().show(supportFragmentManager,LoginPage.TAG)
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
        //TODO this is a test
        lifecycleScope.launch {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && mainViewmodel.externalLocalSongs.value == null){
                var array: List<Song> = arrayListOf<Song>()
                CoroutineHelper.onBackgroundThread { array = this@MainActivity.getMatchData() }
                CoroutineHelper.onMainThread { mainViewmodel.externalLocalSongs.postValue(array.sortedBy { it.title }) }
            }
           // CoroutineHelper.onBackgroundThread {

           //     "TicketMaster".debugLog(ticketMaster.discoverEvents(radius = 100))
             //   val x= ticketMaster.eventDetails("vvG1jZ9KbsbPCD")
              //  "TicketMaster".debugLog(x)
          //  }
        }

        binding.bottomNav.selectedIndex = 1
        binding.bottomNav.menuItemSelectionListener = object : BottomNavigation.OnMenuItemSelectionListener{
            private fun Int.navigationBottomNav(){
                when(this) {
                    R.id.menu_maps -> permissionLocation.checkForAllPermissionsOrContinue{
                        binding.navViewPagerHost.findNavController().navigate(R.id.action_global_maps)
                    }
                    R.id.menu_library -> binding.navViewPagerHost.findNavController().navigate(R.id.action_global_library)
                    R.id.menu_explore -> binding.navViewPagerHost.findNavController().navigate(R.id.action_global_nach_exploreContent)
                }
            }
            override fun onMenuItemReselect(itemId: Int, position: Int, fromUser: Boolean) = itemId.navigationBottomNav()
            override fun onMenuItemSelect(itemId: Int, position: Int, fromUser: Boolean) = itemId.navigationBottomNav()
        }

        //Set main exoplayer
        mainViewmodel.musicPlayer = MusixSpiele(this)
        binding.exoplayer.player = mainViewmodel.musicPlayer.exoplayer
        musicNotification.createPlayerNotification(mainViewmodel.musicPlayer.exoplayer, mediaDescriptionAdapter =  musicNotification.defaultMediaDescriptionAdapter)
        mainViewmodel.musicPlayer.addListener(object:MusixSpiele.OnPlayerStateChange{
            override fun onSeekedToDifferentMediaItem(){
                super.onSeekedToDifferentMediaItem()
                findViewById<TextView>(R.id.trackTitle).text = mainViewmodel.musicPlayer.exoplayer.currentMediaItem?.mediaMetadata?.title
                findViewById<TextView>(R.id.trackArtist).text = mainViewmodel.musicPlayer.exoplayer.currentMediaItem?.mediaMetadata?.artist
                val uri = mainViewmodel.musicPlayer.exoplayer.currentMediaItem?.mediaMetadata?.artworkUri
                if(uri != null) findViewById<ImageView>(R.id.trackArtwork).loadImage(uri)
            }
        })
        binding.exoplayer.findViewById<LikeButton>(R.id.collapsedExoplayerLikeBtn).onClick {
            val mediaID = mainViewmodel.musicPlayer.exoplayer.currentMediaItem?.mediaId

            if(mediaID == null){
                showToast("Play a sound to 'Like' it")
                return@onClick
            }

            it.isLiked = !it.isLiked
            lifecycleScope.launch {
                CoroutineHelper.onBackgroundThread {
                    if(it.isLiked) likedSongsDao.delete(mediaID)
                    else likedSongsDao.insert(MediaID(mediaID))
                }
            }
        }

        binding.exoplayer.onClick {
            ExpandedExoplayer().show(supportFragmentManager,ExpandedExoplayer.TAG)
        }

        binding.regconizeSongMicroPhone.onClick { }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicNotification.cancelNotification()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionLocation.handlePermissionsResult(requestCode,permissions as Array<String?>,grantResults){ allGranted ->
            if(!allGranted) showToast("Permission Denied : Feature can't be used")
            else binding.navViewPagerHost.findNavController().navigate(R.id.action_global_maps)
        }
    }
}