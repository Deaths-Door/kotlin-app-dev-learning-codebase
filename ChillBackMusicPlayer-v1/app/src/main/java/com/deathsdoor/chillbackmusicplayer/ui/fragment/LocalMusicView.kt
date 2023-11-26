package com.deathsdoor.chillbackmusicplayer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.showToast
import com.deathsdoor.chillbackmusicplayer.data.extensions.Sort.sort
import com.deathsdoor.chillbackmusicplayer.data.extensions.UserDataFetching.getMatchData
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.AudioFilesDetect
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.CoroutineHelper
import com.deathsdoor.chillbackmusicplayer.data.music.JAudioTagger
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.databinding.FragmentLocalMusicViewBinding
import it.sephiroth.android.library.bottomnavigation.BottomNavigation
import kotlinx.coroutines.launch
import java.io.File
import java.util.ArrayList

//TODO add more columns in the local music view
//TODO update the child fragment once its gotten the local data
class LocalMusicView : Fragment() {
    private lateinit var _binding: FragmentLocalMusicViewBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLocalMusicViewBinding.inflate(inflater,container,false)
        return _binding.root
    }

    private val viewmodel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    private val audioDetector by lazy { AudioFilesDetect(requireContext()) }
    private val jAudioTagger = JAudioTagger(null)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            if(viewmodel.externalLocalSongs.value != null) return@launch
/*
            val array = arrayListOf<Song>()
            CoroutineHelper.onBackgroundThread {
                audioDetector.extractExternalFileMediaColumns {
                    if(it == null) return@extractExternalFileMediaColumns
              //      array.addAll(requireContext().matchWithData(it.map { MediaID(it) }))
                   it.forEach { path ->
                        jAudioTagger.file = File(path)
                        array.add(
                            Song(
                                jAudioTagger.title,
                                jAudioTagger.artist,
                                jAudioTagger.album,
                                jAudioTagger.genre,
                                jAudioTagger.albumArtist,
                                "TODO detect lyrics file",
                                path,
                                "",
                                jAudioTagger.file!!.name
                            )
                        )
                    }
                }
            }*/
            var array: List<Song> = arrayListOf<Song>()
            CoroutineHelper.onBackgroundThread {
               array = requireContext().getMatchData()
            }
            CoroutineHelper.onMainThread {
           //     viewmodel.externalLocalSongs.postValue(array.sortedBy { it.title })
                viewmodel.externalLocalSongs.postValue(array.sortedBy { it.title })
                context?.showToast("Obtained local songs")
            }
        }

        _binding.bottomNav.menuItemSelectionListener = object:BottomNavigation.OnMenuItemSelectionListener{
            private fun Int.onSelect(){
                when(this){
                    R.id.menuLocalMusicViewAllSongs ->
                        _binding.navView.findNavController().navigate(
                            R.id.action_global_nach_songRecyclerView,
                            viewmodel.externalLocalSongs.value?.let { SongRecyclerView.createBundle(it,viewmodel.orientation.value) }
                        )
                    R.id.menuLocalMusicViewAlbums -> _binding.navView.findNavController().navigate(
                        R.id.action_global_nach_albumRecyclerView,
                        viewmodel.externalLocalSongs.value?.let { list ->
                            AlbumRecyclerView.createBundle(list.sort{ it.album.split("/", "&") },
                                viewmodel.orientation.value, disableCheckAlbumDownload = true)
                        }
                    )
                    R.id.menuLocalMusicViewArtist -> _binding.navView.findNavController().navigate(
                        R.id.action_global_nach_albumRecyclerView,
                        viewmodel.externalLocalSongs.value?.let { list ->
                            AlbumRecyclerView.createBundle(list.sort{it.artist.split("/", "&") },
                                viewmodel.orientation.value, disableCheckAlbumDownload = true)
                        }
                    )
                }
            }
            override fun onMenuItemReselect(itemId: Int, position: Int, fromUser: Boolean) = itemId.onSelect()
            override fun onMenuItemSelect(itemId: Int, position: Int, fromUser: Boolean) = itemId.onSelect()
        }
    }
}
