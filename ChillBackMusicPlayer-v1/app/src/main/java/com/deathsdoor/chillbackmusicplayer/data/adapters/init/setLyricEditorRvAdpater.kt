package com.deathsdoor.chillbackmusicplayer.data.adapters.init

import androidx.recyclerview.widget.LinearLayoutManager
import com.deathsdoor.chillbackmusicplayer.data.adapters.recyclerview.LyricEditorRvAdapter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.customviews.Lyrics
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.minuteToMilliseconds
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.secondToMilliseconds
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.showAlertDialogBox
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.toMinutes
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.toSeconds
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.ClickListeners.onClick
import com.deathsdoor.chillbackmusicplayer.databinding.PopupEditTextBinding
import com.deathsdoor.chillbackmusicplayer.databinding.TimePickerBinding
import com.deathsdoor.chillbackmusicplayer.ui.bottomsheets.LyricEditor
import kotlin.time.Duration.Companion.milliseconds

//TODO make adapter non-null in all adapters setters
//TODO add feature to delete and add lyrics in list
//TODO add feature tom move the lurics from pos to pos

fun LyricEditor.adapter() {
    if(viewmodel.parsedFileContent.value == null) return

    val adapter = LyricEditorRvAdapter(requireContext(),viewmodel.parsedFileContent.value!!,viewmodel.musixSpiele.exoplayer)
    _binding.recyclerview.root.layoutManager = LinearLayoutManager(context)
    _binding.recyclerview.root.adapter = adapter
    adapter.clickListener = object:LyricEditorRvAdapter.OnItemClickListener{
        override fun onItemClicked(lyrics: Lyrics) {
            _binding.recyclerview.root.scrollToPosition(viewmodel.parsedFileContent.value!!.lyrics.indexOf(lyrics))
            viewmodel.musixSpiele.exoplayer.seekTo(lyrics.timeStamp - 2000)
        }
        override fun onTimeStampClicked(lyrics: Lyrics, position: Int) {
            val pair = requireActivity().showAlertDialogBox { TimePickerBinding.inflate(it) }
            val windowBinding = pair.first

            //TODO create custom number picker view
            windowBinding.milliseconds.value = lyrics.timeStamp.milliseconds.inWholeMilliseconds.toInt()
               // .toMilliSecondsBetweenZeroHundred()
            windowBinding.seconds.value = lyrics.timeStamp.toSeconds()
            windowBinding.minutes.value = lyrics.timeStamp.toMinutes().toInt()

            windowBinding.milliseconds.minValue = 0
            windowBinding.milliseconds.maxValue = 99

            windowBinding.seconds.minValue = 0
            windowBinding.seconds.maxValue = 59

            windowBinding.minutes.minValue = 0
            windowBinding.minutes.maxValue = 99
            pair.second.setOnDismissListener {
                val x = this@adapter.viewmodel.parsedFileContent.value!!
                x.lyrics[position].timeStamp = windowBinding.seconds.value.secondToMilliseconds() +
                        windowBinding.minutes.value.minuteToMilliseconds() +
                        windowBinding.milliseconds.value.toLong()
                this@adapter.viewmodel.parsedFileContent.postValue(x)
            }
        }

        override fun onLyricClicked(lyrics: Lyrics) {
            val pair = requireContext().showAlertDialogBox { PopupEditTextBinding.inflate(it) }
            val windowBinding = pair.first
            windowBinding.title.text = "Alter Lyrics"
            windowBinding.editext.setText(lyrics.text)
            windowBinding.button.onClick {
                pair.second.dismiss()
            }
        }
    }
}