package com.example.musiclistener.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.musiclistener.*
import com.example.musiclistener.CommonFunctions.canonicalFileDir
import com.example.musiclistener.CommonFunctions.filterLists
import com.example.musiclistener.CommonFunctions.setLanguage
import com.example.musiclistener.Constants.ALIBYS_LANGUAGES
import com.example.musiclistener.Constants.FB_CURRENT_USER
import com.example.musiclistener.Constants.SETTING_SPRACHE
import com.example.musiclistener.appdatabase.LocalMusicDao
import com.example.musiclistener.appdatabase.PlaylistDao
import com.example.musiclistener.music.MusicPlayer.newQueue
import com.example.musiclistener.music.MusicPlayer.playPlaylist
import com.example.musiclistener.music.MusicViewModel
import com.example.musiclistener.ui.MainActivity
import com.example.musiclistener.ui.SwipeGestures.swipeRvPlaylist
import com.example.musiclistener.ui.bottomsheets.ExpandedExoplayer
import com.example.musiclistener.ui.bottomsheets.MoreSongInfo
import com.example.musiclistener.ui.bottomsheets.SortBy
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@BindingAdapter("loadImg")
fun ImageView.loadImg(imgURL: String)
    =  Glide.with(context).load(imgURL)
        .placeholder(R.drawable.ic_launcher_background) //Default img
        .skipMemoryCache(true)//Caching img if phone offline
        .into(this)
@BindingAdapter("loadImg")
fun ImageView.loadImg(imgURL: Uri)
        =  Glide.with(context).load(imgURL)
        .placeholder(R.drawable.ic_launcher_background) //Default img
        .skipMemoryCache(true)//Caching img if phone offline
        .into(this)
@BindingAdapter("loadImg")
fun ImageView.loadImg(img: Int)
        = Glide.with(context).load(img)
        .placeholder(R.drawable.ic_launcher_background) //Default img
        .skipMemoryCache(true)//Caching img if phone offline
        .into(this)
@BindingAdapter("loadImg")
fun ImageView.loadImg(bitmap: Bitmap)
        = Glide.with(context).load(bitmap)
        .placeholder(R.drawable.ic_launcher_background) //Default img
        .skipMemoryCache(true)//Caching img if phone offline
        .into(this)

@BindingAdapter("loadURL")
fun WebView.loadURL(url:String?){
    this.webViewClient = WebViewClient()
    url?.let { this.loadUrl(it) }
    this.settings.javaScriptEnabled = true
    this.setLayerType(View.LAYER_TYPE_SOFTWARE,null)
}

@JvmName("adapterRvPlaylist")
@DelicateCoroutinesApi
@BindingAdapter("adapterLayoutDirection","adapterViewModel","adapterPlaylistDB","adapterLocalMusicDB", requireAll = true)
fun RecyclerView.adapter (layout: Int, vm: MusicViewModel, playlistAppDB: PlaylistDao, localMusicDB: LocalMusicDao){
    val adapter = vm.userPlaylist.value?.let { PlaylistRvAdapter(it) }
    if(layout == LinearLayoutManager.VERTICAL) this.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
    else this.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
    this.adapter = adapter
    this.swipeRvPlaylist(vm,playlistAppDB)
    adapter?.setOnItemClickListener(object:PlaylistRvAdapter.OnItemClickListener{
        override fun OnLongClick(position: Int) {
            if(!vm.moveToPlaylistView) return
            //TODO show extra methods for playlist and song long click update the ui constants bits for god sakes
        }
        override fun OnItemClick(position: Int) {
            if(vm.moveToPlaylistView){
                vm.currentPlaylist.postValue(vm.userPlaylist.value!![position])
                this@adapter.findNavController().navigate(R.id.action_nav_my_library_to_playlistView)
            }
            else{
                val songData = vm.filteredSongs.value!![position]
                val exists = vm.userPlaylist.value!![position].songsIDs.indexOf(songData.mediaID)
                //only add song if it doesn't exist in playlist
                if(exists == -1) GlobalScope.launch { playlistAppDB.updatePlaylist(vm.userPlaylist.value!![position].addSong(songData)) }
            }
        }
    })
}

@JvmName("adapterRvSong")
@DelicateCoroutinesApi
@BindingAdapter("adapterLayoutDirection","adapterViewModel","adapterDB","fragmentManager", requireAll = true)
fun RecyclerView.adapter(layout: Int, vm: MusicViewModel, playlistAppDB: PlaylistDao, fragmentManager: FragmentManager) {
    val data = if(vm.showAddBtn) vm.filteredSongs.value else vm.currentPlaylist.value?.songs
    val adapter = data?.let { SongRvAdapter(it,vm,vm.showAddBtn) }
    if(layout == LinearLayoutManager.VERTICAL) this.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
    else this.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
    this.adapter = adapter
    adapter?.setOnItemClickListener(object:SongRvAdapter.OnItemClickListener{
        override fun OnItemClick(position: Int) {
            if(vm.showAddBtn) vm.exoPlayer!!.playPlaylist(vm.filteredSongs.value!![position],context.canonicalFileDir())
            else vm.exoPlayer!!.playPlaylist(vm.currentPlaylist.value!!.songs[position],context.canonicalFileDir())
        }
        override fun OnLikeBtnClick(position: Int, liked: Boolean) {
            if(!liked) GlobalScope.launch { playlistAppDB.updatePlaylist(vm.userPlaylist.value!![0].removeSong(vm.userPlaylist.value!![0].songs[position])) }
            else GlobalScope.launch { playlistAppDB.updatePlaylist(vm.userPlaylist.value!![0].addSong(data[position])) }
        }
        override fun OnItemLongClicked(position: Int) {
            if(vm.showAddBtn) MoreSongInfo(vm.filteredSongs.value!![position]).show(fragmentManager, MoreSongInfo.TAG)
            else MoreSongInfo(vm.currentPlaylist.value!!.songs[position]).show(fragmentManager, MoreSongInfo.TAG)
        }
        override fun OnAddBtnClick(position: Int) {
            val popupView: View = LayoutInflater.from(this@adapter.context).inflate(R.layout.add_song_to_playlist,null,false)
            val popupWindow = PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            popupWindow.isFocusable = true
            popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0)
        }
    })
}

@JvmName("adapterRvArtist")
@DelicateCoroutinesApi
@BindingAdapter("adapterData","adapterLayoutDirection","adapterViewModel", requireAll = true)
fun RecyclerView.adapter (data: List<Artist>?, layout: Int, vm: MusicViewModel){
    val adapter = data?.let { ArtistRvAdapter(it) }
    if(layout == LinearLayoutManager.VERTICAL) this.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
    else this.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
    this.adapter = adapter
    adapter?.setOnItemClickListener(object:ArtistRvAdapter.OnItemClickListener{
        override fun OnItemClick(position: Int) {
            vm.currentArtist.postValue(vm.followedArtists.value!![position])
            this@adapter.findNavController().navigate(R.id.action_nav_my_library_to_artistProfile)
        }
    })
}

@JvmName("adapterLvSozailMedien")
@BindingAdapter( "adapterData",requireAll = true)
fun ListView.adapter(data: ArrayList<String>){
    val list = this.context.resources.getStringArray(R.array.sozialMedien)
    val finalData =  (list as List<*>).filterLists((data as List<*>)){ a, b -> a.toString() in b.toString() }
    this.adapter = ArrayAdapter(this.context, android.R.layout.simple_list_item_1,finalData)
    this.setOnItemClickListener { _, _, position, _ ->
        val openURl = Intent(Intent.ACTION_VIEW)
        openURl.data = Uri.parse(data[position])
        this.context.startActivity(openURl)
    }
}

@JvmName("adapterImgViewPager")
@BindingAdapter("adapterViewModel","adapterContext", requireAll = true)
fun ViewPager.adapter(vm: MusicViewModel,context: Context){
    val data = arrayListOf<String>()
    vm.showSongList.value?.forEach{ data.add(it.imgURL) }
    this.adapter = ViewPagerIMGAdapter(context,data)
    this.currentItem = vm.exoPlayer!!.currentMediaItemIndex
}

//TODO remove this fun idiot
@JvmName("adapterWahlenSprache")
@BindingAdapter("adapterContext","adapterActivity","adapterViewModel", requireAll = true)
fun AutoCompleteTextView.adapter(context: Context,activity:FragmentActivity,vm: MusicViewModel){
    val arrayAdapter = ArrayAdapter(context,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,resources.getStringArray(R.array.sprachen))
    this.setAdapter(arrayAdapter)
    this.setOnItemClickListener { _, _, position, _ ->
        vm.userSettings[SETTING_SPRACHE] = ALIBYS_LANGUAGES[position]
        activity.setLanguage(vm)
        startActivity(context,Intent(context,MainActivity::class.java), Bundle())
    }
}


class OnClicks {
    fun navigateToSortedLocalMusic(view: View) {
        Toast.makeText(view.context,"",Toast.LENGTH_SHORT).show()
        view.findNavController().navigate(R.id.action_nav_my_library_to_localMusicView)
    }
    fun playPlaylist(vm:MusicViewModel,context:Context) = vm.exoPlayer!!.newQueue(vm.currentPlaylist.value!!,context.canonicalFileDir())
    fun expandExoplayer(supportFragmentManager:FragmentManager) = ExpandedExoplayer().show(supportFragmentManager, ExpandedExoplayer.TAG)
    fun expandOrCollapse(view:View)
        = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) (view as TextView).isSingleLine = !view.isSingleLine
          else if( (view as TextView).maxLines == 1)  view.maxLines = 100
          else  view.maxLines = 1
    fun sort(parentView: SortBy, thing: Int, vm: MusicViewModel){
        when(thing){
            SORT.A_Z.id-> { vm.userPlaylist.value!!.sortedWith(compareBy({!it.pinned},{it.name})) }
            SORT.Z_A.id -> { vm.userPlaylist.value!!.sortedWith(compareBy<Playlists>{!it.pinned }.thenByDescending {it.name})}
            SORT.MOST_PLAYED.id -> { }
            SORT.MOST_RECENT.id -> {}
            SORT.RECENTLY_PLAYED.id -> {}
            SORT.RECENTLY_ADDED.id -> {}
        }
        parentView.onDestroy()
    }
    fun changeWebsite(vm: MusicViewModel, clicked: Int){
        when (clicked) {
            WEBPAGE.AMAZON_MUSIC.id -> vm.webPageURl.postValue(WEBPAGE.AMAZON_MUSIC.url)
            WEBPAGE.APPLE_MUSIC.id -> vm.webPageURl.postValue(WEBPAGE.APPLE_MUSIC.url)
            WEBPAGE.SPOTIFY.id -> vm.webPageURl.postValue(WEBPAGE.SPOTIFY.url)
            WEBPAGE.TIDAL.id -> vm.webPageURl.postValue(WEBPAGE.TIDAL.url)
            WEBPAGE.YOUTUBE.id -> vm.webPageURl.postValue(WEBPAGE.YOUTUBE.url)
            WEBPAGE.YOUTUBE_MUSIC.id -> vm.webPageURl.postValue(WEBPAGE.YOUTUBE_MUSIC.url)
        }
    }
}


//TODO remove old @strings for settings and put new ones
class OnClickSettings{
    fun chooseLanguage(context: Context,activity: FragmentActivity,vm: MusicViewModel,preferences: SharedPreferences){
        val list: Array<String> = context.resources.getStringArray(R.array.sprachen)
        AlertDialog.Builder(context)
            .setTitle(context.resources.getString(R.string.choose_language))
            .setItems(list) { dialog, pos ->
                vm.userSettings[SETTING_SPRACHE] = list[pos]
                preferences.edit().putString(SETTING_SPRACHE.toString(), vm.userSettings[SETTING_SPRACHE].toString()).apply()
                activity.setLanguage(vm)
                dialog.cancel()
            }
            .create()
            .show()
    }
    fun deleteAccount(context: Context)
        = AlertDialog.Builder(context)
            .setTitle(context.resources.getString(R.string.delete_account))
            .setMessage(context.resources.getString(R.string.delete_account_msg))
            .setPositiveButton(context.resources.getString(R.string.delete)) { _, _ -> FB_CURRENT_USER!!.delete() }
            .setNegativeButton(context.resources.getString(R.string.cancel)){ dialog,_ -> dialog.cancel()}
            .create()
            .show()
    fun logoutAccount(context: Context)
        = AlertDialog.Builder(context)
            .setMessage(context.resources.getString(R.string.account_delete_warning))
            .setPositiveButton(context.resources.getString(R.string.log_out)) { _, _ -> FirebaseAuth.getInstance().signOut() }
            .setNegativeButton(context.resources.getString(R.string.cancel)){ dialog,_ -> dialog.cancel()}
            .setTitle(context.resources.getString(R.string.log_out_msg))
            .create()
            .show()
}

interface ICardLimit {
    fun getCreditLimit(): Int
}
enum class CardType : ICardLimit {
    SILVER { override fun getCreditLimit() = 100000 },
    GOLD { override fun getCreditLimit() = 200000 },
    PLATINUM { override fun getCreditLimit() = 300000 }
}