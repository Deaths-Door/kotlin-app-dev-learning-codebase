package com.deathsdoor.chillbackmusicplayer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.AppDataBase
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Album
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.changeStatusBarColor
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.showAlertDialogBox
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.showToast
import com.deathsdoor.chillbackmusicplayer.data.extensions.UI.createChildFragment
import com.deathsdoor.chillbackmusicplayer.data.extensions.UI.text
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.ClickListeners.onClick
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.CoroutineHelper
import com.deathsdoor.chillbackmusicplayer.data.permissions.StoragePermissionsHandler
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.LibraryViewModel
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.databinding.FragmentLibraryBinding
import com.deathsdoor.chillbackmusicplayer.databinding.PopupEditTextBinding
import com.deathsdoor.chillbackmusicplayer.ui.bottomsheets.AddSongsToCreatedAlbum
import kotlinx.coroutines.launch

class Library : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor(R.color.orange)
    }

    private lateinit var _binding:FragmentLibraryBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLibraryBinding.inflate(inflater,container,false)
        return _binding.root
    }

    private val libraryViewmodel by lazy { ViewModelProvider(this)[LibraryViewModel::class.java] }
    private val mainViewmodel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    private val userAlbumDao by lazy { AppDataBase.dataBase(requireContext()).userAlbumDao()}
    private val likedAlbumDao by lazy { AppDataBase.dataBase(requireContext()).likedSongDao()}
    private val locationPermissionHandler : StoragePermissionsHandler by lazy { StoragePermissionsHandler(requireActivity(), readExternalStorage = true) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeStatusBarColor(R.color.orange)

        lifecycleScope.launch {
            val data =  CoroutineHelper.onBackgroundThread {
                userAlbumDao.getAll()
            }
            CoroutineHelper.onMainThread {
                createChildFragment(AlbumRecyclerView.create(data ?: emptyList()),_binding.rvPlaylist.id)
            }
        }


        _binding.cvLocalSongs.setOnClickListener {
            locationPermissionHandler.checkForAllPermissionsOrContinue {
                findNavController().navigate(R.id.action_library_to_localMusicView)
            }
        }

        _binding.favouriteSongs.onClick {
            lifecycleScope.launch{
                val data = CoroutineHelper.onBackgroundThread { likedAlbumDao.all() }
                CoroutineHelper.onMainThread {
                    //TODO get favourite songs and navigate to albumDetailedInfo
                    findNavController().navigate(R.id.action_global_albumDetailedInfo,)
                }
            }
        }

        _binding.newPlaylist.onClick {
            val pair =  requireActivity().showAlertDialogBox { PopupEditTextBinding.inflate(it) }
            val windowBinding = pair.first

            windowBinding.title.text = "New Playlist"
            windowBinding.editext.hint = "Enter Playlist Title"
            windowBinding.button.text = "Create Playlist"

            libraryViewmodel.createdAlbumTitle?.let { windowBinding.editext.setText(it)  }

            windowBinding.button.onClick button@{ button ->

                if(button.text().isEmpty()){
                    context?.showToast("Enter a title")
                    return@button
                }
                libraryViewmodel.createdAlbumTitle = button.text()

                val album = Album(title = libraryViewmodel.createdAlbumTitle!!)
                mainViewmodel.albumMediaId = album.id

                lifecycleScope.launch{
                    CoroutineHelper.onBackgroundThread {
                        userAlbumDao.insert(album)
                    }
                }
                AddSongsToCreatedAlbum().show(parentFragmentManager,AddSongsToCreatedAlbum.TAG)
            }
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        locationPermissionHandler.handlePermissionsResult(requestCode,permissions,grantResults) { isGranted: Boolean ->
            if(isGranted) findNavController().navigate(R.id.action_library_to_localMusicView)
            else context?.showToast("Permission Denied : Feature cant be used")
        }
      //  if(grantResults.isNotEmpty()){ grantResults.forEach { if(it != PackageManager.PERMISSION_GRANTED)requireContext().showToast("Permission Denied : Feature cant be used")  } }
    }
}
