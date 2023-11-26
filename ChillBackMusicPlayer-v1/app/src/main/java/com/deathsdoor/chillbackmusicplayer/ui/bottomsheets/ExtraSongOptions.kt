package com.deathsdoor.chillbackmusicplayer.ui.bottomsheets

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.AppDataBase
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.showAlertDialogBox
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.showToast
import com.deathsdoor.chillbackmusicplayer.data.extensions.Image.loadImage
import com.deathsdoor.chillbackmusicplayer.data.extensions.UI.changeVisibility
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.AudioFilesDetect
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.ClickListeners.onClick
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.CoroutineHelper
import com.deathsdoor.chillbackmusicplayer.data.permissions.StoragePermissionsHandler
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.databinding.BottomsheetExtraSongOptionsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.deleteFile as delete

/*
TODO Options
*   - Deleting / Removing Song from users view : Song if its a local songs
* */
//TODO maybe set the song var in the mainViewModel to song and access it from there might be better then giving song from bottomsheet to BottomSheet
class ExtraSongOptions : BottomSheetDialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { bottomSheet ->
                val behaviour = BottomSheetBehavior.from(bottomSheet)
                val layoutParams = bottomSheet.layoutParams
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                bottomSheet.layoutParams = layoutParams
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }

        }
        return dialog
    }

    private lateinit var _binding : BottomsheetExtraSongOptionsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomsheetExtraSongOptionsBinding.inflate(inflater,container,false)
        return _binding.root
    }

    private val likeDao by lazy { AppDataBase.dataBase(requireContext()).likedSongDao() }
    private val mainViewModel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    private val audioDetector by lazy { AudioFilesDetect(requireContext(),mainViewModel.song.value?.mediaID) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.song.observe(viewLifecycleOwner) { song ->
            if(song == null) {
                this@ExtraSongOptions.dismiss()
                return@observe
            }

            _binding.image.loadImage(song.imageURI())
            _binding.title.text = song.title
            _binding.artist.text = song.artist

            //IF from api not a external local music file
            if (audioDetector.isInternalFile && !audioDetector.isExternalFile) {
                _binding.musixEditor.changeVisibility()
                _binding.deleteFromDevice.changeVisibility()
                _binding.hideFromAppView.changeVisibility()
            }
            //IF external local music file not from api
            else if (!audioDetector.isInternalFile && audioDetector.isExternalFile) _binding.btnDownload.changeVisibility()

            _binding.musixEditor.onClick {
                MusixEditor(song).show(
                    parentFragmentManager,
                    MusixEditor.TAG
                )
            }

            lifecycleScope.launch {
                val isLiked =  CoroutineHelper.onBackgroundThread { likeDao.isLiked(song.mediaID) }

                CoroutineHelper.onMainThread{
                    if (isLiked) {
                        _binding.btnLike.text = "Liked"
                        _binding.btnLike.icon = context?.loadImage(com.like.view.R.drawable.heart_on)
                    } else {
                        _binding.btnLike.text = "Not Liked"
                        _binding.btnLike.icon = context?.loadImage(com.like.view.R.drawable.heart_off)
                    }
                }
            }
            _binding.btnLike.onClick {
                if (it.text == "Liked") {
                    it.text = "Not Liked"
                    it.icon = context?.loadImage(com.like.view.R.drawable.heart_on)
                } else {
                    it.text = "Liked"
                    it.icon = context?.loadImage(com.like.view.R.drawable.heart_off)
                }

                lifecycleScope.launch {
                    CoroutineHelper.onBackgroundThread{
                        if (likeDao.isLiked(song.mediaID)) likeDao.delete(song.mediaID)
                        else likeDao.insert(song.toMediaIDSong())
                    }
                }
            }

            _binding.lyricEditor.onClick {
                LyricEditor(song).show(parentFragmentManager,LyricEditor.TAG)
            }

            _binding.btnAddToAlbum.onClick {
                AddSongToExistingAlbum().show(parentFragmentManager, AddSongToExistingAlbum.TAG)
            }

            _binding.deleteFromDevice.onClick {
                context?.showAlertDialogBox(
                    "Delete File From Device",
                    "Are you sure you wanna delete the file from the device?",
                    "Yes",
                    "No"
                ) { isSuccess, dialog ->
                    if (!isSuccess) {
                        dialog.dismiss()
                        return@showAlertDialogBox
                    }
                    storagePermissionsHandler.checkForAllPermissionsOrContinue {
                        AudioFilesDetect(
                            requireContext(),
                            song.mediaID
                        ).extractExternalFileMediaColumns {
                            if (it == null) return@extractExternalFileMediaColumns
                            path = it[0]
                        }
                        context?.delete(path)
                    }
                }
            }

            _binding.hideFromAppView.onClick {
                //TODO hide Song From App View
            }
        }
    }
    private val storagePermissionsHandler by lazy { StoragePermissionsHandler(requireActivity(), writeExternalStorage = true) }
    private lateinit var path : String
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        storagePermissionsHandler.handlePermissionsResult(requestCode,permissions as Array<String?>,grantResults){ allGranted ->
            if(!allGranted) context?.showToast("Permission Denied : Feature can't be used")
            else context?.delete(path)
        }
    }
}