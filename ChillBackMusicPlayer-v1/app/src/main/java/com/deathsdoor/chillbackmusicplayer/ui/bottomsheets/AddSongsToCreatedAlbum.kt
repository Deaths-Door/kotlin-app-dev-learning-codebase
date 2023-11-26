package com.deathsdoor.chillbackmusicplayer.ui.bottomsheets

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song
import com.deathsdoor.chillbackmusicplayer.data.extensions.UI.createChildFragment
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.CoroutineHelper
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.databinding.BottomsheetAddTrackToNewAlbumBinding
import com.deathsdoor.chillbackmusicplayer.ui.fragment.SongRecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class AddSongsToCreatedAlbum: BottomSheetDialogFragment() {

    companion object{
        const val TAG = "AddTrackToNewAlbum"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { bottomSheet ->
                val behaviour = BottomSheetBehavior.from(bottomSheet)
                val layoutParams = bottomSheet.layoutParams
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
                bottomSheet.layoutParams = layoutParams
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }
    private lateinit var _binding : BottomsheetAddTrackToNewAlbumBinding
    private val mainViewmodel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomsheetAddTrackToNewAlbumBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            val fetchedData =  CoroutineHelper.onBackgroundThread {
                //TODO add recommendation system for in app an remove songs in playlist from list and get songs from api to list
                //TODO shuffle them
                val fetchedData = arrayListOf<Song>()
                mainViewmodel.externalLocalSongs.value?.let { fetchedData.addAll(it) }
                return@onBackgroundThread fetchedData
            }
            CoroutineHelper.onMainThread {
                createChildFragment(SongRecyclerView.create(fetchedData, disableForwardNavigation = true),_binding.recyclerview.id)
            }
        }
       /* this.lifecycleScope.updateUIAfterFetchingData(object: Extensions.SyncAsync<List<Song>> {
            override fun async(): List<Song> {
                val fetchedData = emptyList<Song>()
                mainViewmodel.externalLocalSongs.value?.let { return it + fetchedData }
                return fetchedData
            }
            override fun sync(it: List<Song>) {
                createChildFragment(SongRecyclerView.create(it, disableForwardNavigation = true),_binding.recyclerview.id)
            }
        })*/
    }
}