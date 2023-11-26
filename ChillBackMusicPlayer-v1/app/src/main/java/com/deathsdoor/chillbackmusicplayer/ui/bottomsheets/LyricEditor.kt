package com.deathsdoor.chillbackmusicplayer.ui.bottomsheets

import android.app.Dialog
import android.app.SearchManager
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.deathsdoor.chillbackmusicplayer.data.adapters.init.adapter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.Song
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.customviews.Lyrics
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.customviews.SongLyrics
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.showAlertDialogBox
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.showToast
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.ClickListeners.onClick
import com.deathsdoor.chillbackmusicplayer.data.music.MusixSpiele
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.LyricEditorViewModel
import com.deathsdoor.chillbackmusicplayer.databinding.BottomsheetLyricEditorBinding
import com.deathsdoor.chillbackmusicplayer.databinding.PopupEditTextBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.FileWriter


class LyricEditor @Deprecated("use nav compoment") constructor(val song:Song): BottomSheetDialogFragment() {
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

    lateinit var _binding:BottomsheetLyricEditorBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomsheetLyricEditorBinding.inflate(inflater,container,false)
        return _binding.root
    }
    //TODO add save button to tell where to save file for now put in internal storage
    val viewmodel by lazy { ViewModelProvider(this)[LyricEditorViewModel::class.java] }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.musixSpiele = MusixSpiele(requireContext())
        viewmodel.musixSpiele.addSongToQueue(song)

        _binding.searchOnline.onClick {
            searchOnInternet("${song.title} by ${song.artist} from album ${song.album} lyrics ")
        }

        _binding.paste.onClick {
            viewmodel.parsedFileContent.postValue(SongLyrics(SongLyrics.generateLrcFileFormat(getFromClipBoard(0))))
        }

        _binding.transcribe.onClick {
            context?.showToast("Feature still in development")
        }

        _binding.addLyric.onClick {
            viewmodel.parsedFileContent.postValue(
                viewmodel.parsedFileContent.value
            )
            val x = viewmodel.parsedFileContent.value
            (x?.lyrics as? ArrayList?)?.add(Lyrics(x.lyrics.last().timeStamp,"Edit Text"))
            viewmodel.parsedFileContent.postValue(x)
        }
        viewmodel.parsedFileContent.observe(viewLifecycleOwner){
            adapter()
        }

        _binding.save.onClick main@{
            val pair = requireContext().showAlertDialogBox { PopupEditTextBinding.inflate(it) }
            val windowBinding = pair.first
            windowBinding.title.text = "Enter File Title"
            windowBinding.editext.setText("${viewmodel.musixSpiele.exoplayer.currentMediaItem!!.mediaMetadata.title}_lyrics" )
            windowBinding.button.onClick {
                if(!windowBinding.editext.text.toString().validFileName()) {
                    context?.showToast("Enter Valid File Name")
                    return@onClick
                }
                File(Environment.getExternalStorageDirectory().path + windowBinding.editext.text).createToWriteIntoFile(viewmodel.parsedFileContent.value!!.asLrc())
                pair.second.dismiss()
            }
        }
        //TODO on save
        /**
         *
         *      "lyricEditing.lrc"
        .internalFile(requireContext())
        .createToWriteIntoFile(SongLyrics.generateLrcFileFormat(getFromClipBoard(0)))

         */
        /**
         *  lifecycleScope.launch {
        CoroutineHelper.onBackgroundThread {

        lyricEditorViewModel.parsedFileContent.postValue(
        SongLyrics(listOf(Lyrics(1000,"TO HELL WITH YOU"),Lyrics(1000,"TO HELL WITH YOU"),Lyrics(1000,"TO HELL WITH YOU"),Lyrics(1000,"TO HELL WITH YOU"),Lyrics(1000,"TO HELL WITH YOU")))
        //    SongLyrics.parseLyricFile(lyricEditorViewModel.filePath)
        )
        }
        }
        lyricEditorViewModel.parsedFileContent.observe(viewLifecycleOwner){
        adapter()
        }
         */
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.data?.let{
            if(resultCode == FILE_EXOPLORER_REQUEST_CODE) viewmodel.parsedFileContent.postValue(SongLyrics(File(it.path!!)))
        }
    }
    //TODO do this
    companion object{
        private const val FILE_EXOPLORER_REQUEST_CODE = 69
        @Deprecated("use nav complement")
        const val TAG = "LyricEditor"
    }
    private fun String.validFileName(): Boolean = this.matches("[/\\\\?%*:|\"<>]".toRegex())
    private fun searchOnInternet(query:String){
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY, query)
        startActivity(intent)
    }
    private fun getFromClipBoard(index:Int): String {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return clipboard.primaryClip?.getItemAt(index)?.text.toString()
    }
    private fun File.createToWriteIntoFile(contents:String){
        if (this.parentFile?.exists() == false) this.parentFile?.mkdirs()
        if(!this.exists()) this.createNewFile()
        val fileWriter = FileWriter(this)
        fileWriter.write(contents)
        fileWriter.flush()
        fileWriter.close()
    }
}