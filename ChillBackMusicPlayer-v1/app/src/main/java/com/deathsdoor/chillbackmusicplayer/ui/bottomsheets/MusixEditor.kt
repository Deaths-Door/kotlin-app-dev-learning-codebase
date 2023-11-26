package com.deathsdoor.chillbackmusicplayer.ui.bottomsheets

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.showAlertDialogBox
import com.deathsdoor.chillbackmusicplayer.data.extensions.UI.text
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.AudioFilesDetect
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.ClickListeners.onClick
import com.deathsdoor.chillbackmusicplayer.data.music.JAudioTagger
import com.deathsdoor.chillbackmusicplayer.databinding.FragmentMusixEditorBinding
import com.deathsdoor.chillbackmusicplayer.databinding.PopupEditMetadataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

//TODO set as Ringtone
//TODO only available for local music files
//TODO add cut / copy / paste function to editor / edit meta data
//TODO set as Ringtone
class MusixEditor(val song: Song) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "MusixEditor"
    }

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

    private lateinit var _binding : FragmentMusixEditorBinding
    private lateinit var jAudioTagger : JAudioTagger
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       _binding = FragmentMusixEditorBinding.inflate(inflater,container,false)
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AudioFilesDetect(requireContext(),song.mediaID).extractExternalFileMediaColumns {
            jAudioTagger = JAudioTagger(it!![0])
        }

       _binding.waveformSeekBar.setSampleFrom(jAudioTagger.file!!.absoluteFile)

        //TODO add all fields and reset buttons
        _binding.editMetaData.onClick {
            val popupView = requireContext().showAlertDialogBox { PopupEditMetadataBinding.inflate(LayoutInflater.from(context)) }
            val popUpWindowBinding = popupView.first
            val popupWindow = popupView.second

            popUpWindowBinding.title.setText(jAudioTagger.title)
            popUpWindowBinding.artist.setText(jAudioTagger.artist)
            popUpWindowBinding.album.setText(jAudioTagger.album)
            popUpWindowBinding.genre.setText(jAudioTagger.genre)

            popUpWindowBinding.button.onClick {
                popupWindow.dismiss()

                 jAudioTagger.changeTitle(popUpWindowBinding.title.text())
                 jAudioTagger.changeArtist(popUpWindowBinding.artist.text())
                 jAudioTagger.changeAlbum(popUpWindowBinding.album.text())
                 jAudioTagger.changeGenre(popUpWindowBinding.genre.text())
            }
        }
    }
}

