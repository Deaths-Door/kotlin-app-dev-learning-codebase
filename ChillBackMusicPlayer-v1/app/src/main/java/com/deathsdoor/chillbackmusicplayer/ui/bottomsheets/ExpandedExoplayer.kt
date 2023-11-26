package com.deathsdoor.chillbackmusicplayer.ui.bottomsheets

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.deathsdoor.chillbackmusicplayer.data.extensions.Image.loadImage
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.databinding.BottomsheetExpandedExoplayerBinding
import com.deathsdoor.chillbackmusicplayer.databinding.BottomsheetExpandedExoplayerBinding.inflate
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExpandedExoplayer : BottomSheetDialogFragment(){
    companion object {
        const val TAG = "ExpandedExoplayer"
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

    private lateinit var _binding: BottomsheetExpandedExoplayerBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = inflate(inflater,container,false)
        return _binding.root
    }

    private val mainViewModel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    private val exoplayer get() = mainViewModel.musicPlayer.exoplayer
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.exoplayer.player = exoplayer
        exoplayer.mediaMetadata.artworkUri?.let { _binding.imageView.loadImage(it) }

       // _binding.lyricView.addSongLyric(SongLyrics(testLyrics))
 //       _binding.lyricView.setCurrentLyrics(0)
    //    _binding.lyricView.bindWithExoplayer(mainViewModel.musicPlayer.exoplayer)
    }
}
/*
@Deprecated("just for test")
private const val testLyrics =
        "[00:09.64]Hey, I was doing just fine before I met you\n" +
        "[00:14.39]I drink too much and that's an issue but I'm okay\n" +
        "[00:20.14]Hey, you tell your friends it was nice to meet them\n" +
        "[00:24.89]But I hope I never see them again\n" + "" +
        "[00:29.89]I know it breaks your heart\n" +
        "[00:33.14]Moved to the city in a broke down car\n" +
        "[00:35.14]And four years, no calls\n" + "" +
        "[00:37.64]Now you're looking pretty in a hotel bar\n" + "[00:40.14]And I can't stop\n" +
        "[00:42.64]No, I can't stop\n" + "[00:48.14]So baby pull me closer in the backseat of your Rover\n" + "[00:54.14]That I know you can't afford\n" + "[00:56.39]Bite that tattoo on your shoulder\n" +
        "[00:59.64]Pull the sheets right off the corner\n" + "[01:02.39]Of the mattress that you stole\n" + "[01:04.64]From your roommate back in Boulder\n" + "[01:07.39]We ain't ever getting older\n" + "[01:12.64]We ain't ever getting older\n" + "[01:22.64]We ain't ever getting older\n" + "[01:30.14]You look as good as the day I met you\n" +
        "[01:34.64]I forget just why I left you, I was insane\n" +
        "[01:38.64]Stay and play that Blink-182 song\n" +
        "[01:44.64]That we beat to death in Tucson, okay\n" +
        "[01:47.39]I know it breaks your heart\n" +
        "[01:51.14]Moved to the city in a broke down car\n" +
        "[01:54.14]And four years, no call\n" +
        "[01:58.14]Now I'm looking pretty in a hotel bar\n" +
        "[02:00.64]And I can't stop\n" +
        "[02:04.14]No, I can't stop\n" +
        "[02:10.14]So baby pull me closer in the backseat of your Rover\n" +
        "[02:15.14]That I know you can't afford\n" +
        "[02:17.89]Bite that tattoo on your shoulder\n" +
        "[02:20.39]Pull the sheets right off the corner\n" +
        "[02:22.89]Of the mattress that you stole\n" +
        "[02:25.14]From your roommate back in Boulder\n" +
        "[02:27.89]We ain't ever getting older\n" +
        "[02:35.14]We ain't ever getting older\n" +
        "[02:40.14]We ain't ever getting older\n" +
        "[02:50.89]So baby pull me closer in the backseat of your Rover\n" +
        "[02:56.14]That I know you can't afford\n" +
        "[03:00.14]Bite that tattoo on your shoulder\n" +
        "[03:03.39]Pull the sheets right off the corner\n" +
        "[03:05.89]Of the mattress that you stole\n" +
        "[03:09.89]From your roommate back in Boulder\n" +
        "[03:12.64]We ain't ever getting older\n" +
        "[03:15.64]We ain't ever getting older (we ain't ever getting older)\n" +
        "[03:17.39]We ain't ever getting older (we ain't ever getting older)\n" +
        "[03:18.64]We ain't ever getting older (we ain't ever getting older)\n" +
        "[03:22.64]We ain't ever getting older\n" +
        "[03:24.39]We ain't ever getting older\n" +
        "[03:28.64]No we ain't ever getting older\n" +
        "[03:39.80] We ain't ever getting older\n" +
        "[03:49.80] No we ain't ever getting older"
*/
