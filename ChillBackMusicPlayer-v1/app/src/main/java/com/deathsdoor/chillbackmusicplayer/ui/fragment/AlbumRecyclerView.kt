package com.deathsdoor.chillbackmusicplayer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.deathsdoor.chillbackmusicplayer.data.Constants
import com.deathsdoor.chillbackmusicplayer.data.adapters.init.setAlbumAdapter
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.AppDataBase
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Album
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.AlbumRvViewModel
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.databinding.FragmentRecyclerViewBinding

class AlbumRecyclerView : Fragment() {
    lateinit var _binding : FragmentRecyclerViewBinding
    val mainViewModel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    val albumRvViewModel by lazy { ViewModelProvider(this)[AlbumRvViewModel::class.java] }
    val userAlbumsDao by lazy { AppDataBase.dataBase(requireContext()).userAlbumDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.valueFromBundle { albums, orientation, disableForwardNavigation,disableCheckAlbumDownload:Boolean ->
            albumRvViewModel.displayedAlbums.postValue(albums)
            albumRvViewModel.orientation.postValue(orientation)
            albumRvViewModel.disableForwardNavigation = disableForwardNavigation
            albumRvViewModel.disableCheckAlbumDownload = disableCheckAlbumDownload
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecyclerViewBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumRvViewModel.displayedAlbums.observe(viewLifecycleOwner){ setAlbumAdapter() }
        albumRvViewModel.orientation.observe(viewLifecycleOwner){ setAlbumAdapter() }
    }

    companion object{
        private const val DISPLAY_ALBUMS = "displayedAlbums"
        private const val DISABLE_FORWARD_NAVIGATION = "disableForwardNavigation"
        private const val DISABLE_CHECK_ALBUM_DOWNLOADED = "disableCheckAlbumDownload"
        private const val ORIENTATION = "orientation"

        fun create(data:List<Album>, orientation: Constants.ORIENTATION? = Constants.ORIENTATION.VERTICAL, disableForwardNavigation:Boolean = false): AlbumRecyclerView
            = AlbumRecyclerView().also { it.arguments = createBundle(data,orientation,disableForwardNavigation) }

        fun createBundle(data:List<Album>, orientation: Constants.ORIENTATION? = Constants.ORIENTATION.VERTICAL, disableForwardNavigation:Boolean = false, disableCheckAlbumDownload:Boolean = false): Bundle {
            val bundle =  Bundle()
            bundle.putParcelableArray(DISPLAY_ALBUMS, data.toTypedArray())
            if(orientation == null) bundle.putParcelable(ORIENTATION,Constants.ORIENTATION.VERTICAL)
            else bundle.putParcelable(ORIENTATION,orientation)
            bundle.putBoolean(DISABLE_FORWARD_NAVIGATION,disableForwardNavigation)
            bundle.putBoolean(DISABLE_CHECK_ALBUM_DOWNLOADED,disableCheckAlbumDownload)
            return bundle
        }

        private inline fun Bundle.valueFromBundle(crossinline action: (albums:List<Album>?, orientation: Constants.ORIENTATION, disableForwardNavigation:Boolean, disableCheckAlbumDownload:Boolean) -> Unit) {
            action((this.getParcelableArray(DISPLAY_ALBUMS) as Array<Album>?)?.asList(),getParcelable(ORIENTATION) ?: Constants.ORIENTATION.VERTICAL, getBoolean(DISABLE_FORWARD_NAVIGATION),getBoolean(DISABLE_CHECK_ALBUM_DOWNLOADED))
        }
    }
}