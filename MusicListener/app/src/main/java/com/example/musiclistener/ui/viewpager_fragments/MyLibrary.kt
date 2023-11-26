package com.example.musiclistener.ui.viewpager_fragments

import android.os.Bundle
import android.util.Log.d
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import android.content.Context
import com.example.musiclistener.Constants
import com.example.musiclistener.Playlists
import com.example.musiclistener.R
import com.example.musiclistener.adapters.ArtistRvAdapter
import com.example.musiclistener.appdatabase.AppDataBase
import com.example.musiclistener.ImageFunctions.loadImg
import com.example.musiclistener.adapters.OnClicks
import com.example.musiclistener.databinding.FragmentMyLibraryBinding
import com.example.musiclistener.music.MusicViewModel
import com.example.musiclistener.ui.bottomsheets.SortBy
import kotlinx.android.synthetic.main.create_new_playlist.view.*
import kotlinx.android.synthetic.main.fragment_my_library.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyLibrary : Fragment() {
    private lateinit var _binding : FragmentMyLibraryBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMyLibraryBinding.inflate(layoutInflater,container,false)
            .apply {
                vm = ViewModelProvider(requireActivity())[MusicViewModel::class.java]
                layoutDirection = LinearLayoutManager.HORIZONTAL
                onClick = OnClicks()
            }
        return _binding.root
    }

    private val appDB by lazy { Room.databaseBuilder(requireContext(), AppDataBase::class.java, Constants.MUSIC_DATA_DB).build().playlistDao()}
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Set UserProfile img
        my_library_user_profile_img.loadImg("")
        my_library_user_profile_img.setOnClickListener { findNavController().navigate(R.id.action_nav_my_library_to_userProfile) }

        //New Playlist
        myLibrary_new_playlist.setOnClickListener {
            val popupView: View = LayoutInflater.from(activity).inflate(R.layout.create_new_playlist,null,false)
            val popupWindow = PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            popupWindow.isFocusable = true
            popupView.create_new_playlist_btnSave.setOnClickListener{
                if((it as TextView).text.toString() != "")
                    GlobalScope.launch { appDB.insertPlaylist(Playlists(name = popupView.create_new_playlist_title.text.toString())) }
                else Toast.makeText(requireContext(),"Enter Title",Toast.LENGTH_SHORT).show()
                this.onDestroy()
            }
            popupWindow.showAtLocation(popupView,Gravity.CENTER,0,0)
        }

        //Sort songs by smth
        my_library_sort_songs.text = resources.getString(R.string.alphabetically_a_z)
        my_library_sort_songs.setOnClickListener{ SortBy().show(requireFragmentManager(),SortBy.TAG) }
    }
}