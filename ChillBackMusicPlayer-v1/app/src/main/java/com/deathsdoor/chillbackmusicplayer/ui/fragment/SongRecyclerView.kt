package com.deathsdoor.chillbackmusicplayer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.deathsdoor.chillbackmusicplayer.data.Constants
import com.deathsdoor.chillbackmusicplayer.data.adapters.init.adapter
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.AppDataBase
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.SongRvViewModel
import com.deathsdoor.chillbackmusicplayer.databinding.FragmentRecyclerViewBinding

class SongRecyclerView : Fragment() {
    lateinit var _binding: FragmentRecyclerViewBinding
    val mainViewModel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    val songRvViewModel by lazy { ViewModelProvider(this)[SongRvViewModel::class.java] }
    val likedSongDao by lazy { AppDataBase.dataBase(requireContext()).likedSongDao() }
    val userAlbumDao by lazy { AppDataBase.dataBase(requireContext()).userAlbumDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.valueFromBundle { songs, orientation,disableForwardNavigation ->
            songRvViewModel.displayedSongs.postValue(songs)
            songRvViewModel.orientation.postValue(orientation)
            songRvViewModel.disableForwardNavigation = disableForwardNavigation
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecyclerViewBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songRvViewModel.displayedSongs.observe(viewLifecycleOwner){
            if(it == null) return@observe
            adapter()
        //    _binding.root.setSongAdapter(mainViewModel,songRvViewModel,likedSongDao,userAlbumDao)
        }
        songRvViewModel.orientation.observe(viewLifecycleOwner){
            if(it == null) return@observe
            adapter()
          //  _binding.root.setSongAdapter(mainViewModel,songRvViewModel,likedSongDao,userAlbumDao)
        }
    }

    companion object{
        private const val DISPLAY_SONGS = "displayedSongs"
        private const val ORIENTATION = "orientation"
        private const val DISABLE_FORWARD_NAVIGATION = "disableForwardNavigation"

        fun create(data:List<Song>, orientation: Constants.ORIENTATION? = Constants.ORIENTATION.VERTICAL, disableForwardNavigation: Boolean = false): SongRecyclerView
            = SongRecyclerView().also { it.arguments = createBundle(data,orientation,disableForwardNavigation) }

        fun createBundle(data:List<Song>, orientation: Constants.ORIENTATION? = Constants.ORIENTATION.VERTICAL, disableForwardNavigation: Boolean =false): Bundle {
            val bundle =  Bundle()
            bundle.putParcelableArray(DISPLAY_SONGS,data.toTypedArray())
            if(orientation == null) bundle.putParcelable(ORIENTATION,Constants.ORIENTATION.VERTICAL)
            else bundle.putParcelable(ORIENTATION,orientation)
            bundle.putBoolean(DISABLE_FORWARD_NAVIGATION,disableForwardNavigation)
            return bundle
        }
        private inline fun Bundle.valueFromBundle(crossinline action:(songs:List<Song>?, orientation:Constants.ORIENTATION, disableForwardNavigation:Boolean) -> Unit,) {
            action((this.getParcelableArray(DISPLAY_SONGS) as? Array<Song>)?.asList(), getParcelable(ORIENTATION) ?: Constants.ORIENTATION.VERTICAL,this.getBoolean(DISABLE_FORWARD_NAVIGATION))
        }
    }
}