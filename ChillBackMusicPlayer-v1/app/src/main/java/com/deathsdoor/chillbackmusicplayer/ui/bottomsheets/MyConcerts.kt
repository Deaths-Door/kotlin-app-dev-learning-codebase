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
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.CoroutineHelper
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MapsEventViewModel
import com.deathsdoor.chillbackmusicplayer.databinding.BottomsheetMyConcertsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class MyConcerts: BottomSheetDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
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

    private lateinit var _binding: BottomsheetMyConcertsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomsheetMyConcertsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    private val mapsEventViewModel by lazy { ViewModelProvider(requireActivity())[MapsEventViewModel::class.java] }
    private val userEventDao by lazy { AppDataBase.dataBase(requireContext()).userEventsDao() }
    private val eventInfoDao by lazy { AppDataBase.dataBase(requireContext()).cachedEventsDao() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            CoroutineHelper.onBackgroundThread {
                val data = userEventDao.all()
                data?.forEach { it.matchWithData(eventInfoDao) }
                mapsEventViewModel.userEvents.postValue(data)
            }
        }
         _binding.recyclerview.root
    }
}