package com.deathsdoor.chillbackmusicplayer.ui.bottomsheets

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.AppDataBase
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.CoroutineHelper
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.databinding.BottomsheetAddTrackToPlaylistBinding
import com.deathsdoor.chillbackmusicplayer.ui.fragment.AlbumRecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class AddSongToExistingAlbum: BottomSheetDialogFragment() {

    companion object {
        const val TAG = "AddSongsToExistingAlbum"
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

    private lateinit var _binding : BottomsheetAddTrackToPlaylistBinding
    private val mainViewmodel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    private val dao by lazy { AppDataBase.dataBase(requireContext()).userAlbumDao() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomsheetAddTrackToPlaylistBinding.inflate(inflater,container,false)
        return _binding.root
    }

    private val mainViewModel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val data = CoroutineHelper.onBackgroundThread { dao.getAll() }
            CoroutineHelper.onMainThread {
                _binding.recyclerview.findNavController()
                    .navigate(
                        R.id.action_global_nach_albumRecyclerView,
                        data?.let { AlbumRecyclerView.createBundle(disableForwardNavigation = true, data = data) }
                    )
            }
        }

        mainViewmodel.song.observe(viewLifecycleOwner){
            if(it == null) this.dismiss()
        }
    }
}