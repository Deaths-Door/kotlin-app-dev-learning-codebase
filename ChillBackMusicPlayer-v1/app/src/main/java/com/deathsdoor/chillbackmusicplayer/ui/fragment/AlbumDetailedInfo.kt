package com.deathsdoor.chillbackmusicplayer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Album
import com.deathsdoor.chillbackmusicplayer.data.extensions.Image.loadImage
import com.deathsdoor.chillbackmusicplayer.data.extensions.UI.createChildFragment
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.AlbumDetailedViewModel
import com.deathsdoor.chillbackmusicplayer.databinding.FragmentAlbumDetailedInfoBinding

class AlbumDetailedInfo: Fragment() {
    private lateinit var _binding : FragmentAlbumDetailedInfoBinding
    private val albumDetailedViewModel by lazy { ViewModelProvider(this)[AlbumDetailedViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.valueFromBundle(albumDetailedViewModel.album::postValue)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAlbumDetailedInfoBinding.inflate(inflater,container,false)
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albumDetailedViewModel.album.observe(viewLifecycleOwner){
            if(it == null) return@observe
            
            _binding.image.loadImage(it.imgURL)
            _binding.title.text = it.title
            _binding.artist.text = it.albumArtist
            createChildFragment(SongRecyclerView.create(it.songs),_binding.rvSong.id)
        }

    }

    companion object{
        private const val ALBUM = "album"

        fun create(data: Album): AlbumDetailedInfo = AlbumDetailedInfo().also { it.arguments = createBundle(data) }
        fun createBundle(data: Album): Bundle =
            Bundle().apply {
                putParcelable(ALBUM, data)
            }
        private inline fun Bundle.valueFromBundle(crossinline action:(album: Album?) -> Unit) {
            action(this.getParcelable(ALBUM))
        }
    }
}