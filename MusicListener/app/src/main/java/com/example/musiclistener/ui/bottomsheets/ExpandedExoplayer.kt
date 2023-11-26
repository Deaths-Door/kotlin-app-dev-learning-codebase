package com.example.musiclistener.ui.bottomsheets

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.musiclistener.R
import com.example.musiclistener.adapters.ViewPagerIMGAdapter
import com.example.musiclistener.databinding.BottomSheetExpandedExoplayerBinding
import com.example.musiclistener.music.MusicDownloading.localFileExists
import com.example.musiclistener.music.MusicViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_expanded_exoplayer.*
import java.io.File

class ExpandedExoplayer: BottomSheetDialogFragment(), ViewPager.OnPageChangeListener{
    companion object{ const val TAG = "ExpandedExoplayerBottomSheet" }
    private lateinit var _binding : BottomSheetExpandedExoplayerBinding
    private var handler = Handler()
    private var runnable: Runnable? =  null
    private val delay = 100
    private var prevExoPos = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetExpandedExoplayerBinding.inflate(inflater,container,false)
            .apply {
                vm = ViewModelProvider(requireActivity())[MusicViewModel::class.java]
            }
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prevExoPos = _binding.vm!!.exoPlayer?.currentMediaItemIndex ?: 0

        //adding imgs to swipe
        expanded_bottom_sheet_exo_player_img_view_pager.setOnPagefChangeListener(this)

        //updating current data
        update()

        //setting music exoplayer
        expanded_bottom_sheet_exo_player.player = _binding.vm!!.exoPlayer
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            //Full screen
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
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int){}
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageSelected(position: Int){
        //Change Current Song Playing
        changeInExoPos( _binding.vm!!.exoPlayer!!.currentMediaItemIndex)
      //  if(position <  _binding.vm!!.exoPlayer!!.currentMediaItemIndex)  _binding.vm!!.exoPlayer!!.previous()
      //  else  _binding.vm!!.exoPlayer!!.next()
       // update()
    }
    override fun onPause() { super.onPause();
        runnable?.let { handler.removeCallbacks(it) } }
    override fun onResume() {
        val postDelayed = handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            _binding.vm!!.exoPlayer?.let { expanded_bottom_sheet_lyrics_view.setCurrentTimeMillis(it.currentPosition) }
        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }
    //TODO when lyrics not present app crashes
    private fun update(){
        val name = "${ _binding.vm!!.exoPlayer?.currentMediaItem?.mediaId}.lrc"
        val fileDir = requireActivity().filesDir.canonicalFile
       // if(localFileExists(name,fileDir)) expanded_bottom_sheet_lyrics_view.setLyricFile(File(File(fileDir, ""),name))
       // else downloadLRC( _binding.vm!!.currentPlaylistData.songs[ _binding.vm!!.exoPlayer!!.currentMediaItemIndex].mediaID,fileDir,requireContext())
        if( _binding.vm!!.exoPlayer != null &&  _binding.vm!!.currentPlaylistData.value!!.songs.isNotEmpty()){
            //No vid
            if( _binding.vm!!.currentPlaylistData.value!!.songs[ _binding.vm!!.exoPlayer!!.currentMediaItemIndex].videoURL == "") {
                expanded_bottom_sheet_exo_player_video.visibility = View.GONE
            }
            else {
                val videoExoplayer = ExoPlayer.Builder(requireContext()).build()
                expanded_bottom_sheet_exo_player_video.player = videoExoplayer
                val uri =
                    if(localFileExists("${ _binding.vm!!.currentPlaylistData.value!!.songs[ _binding.vm!!.exoPlayer!!.currentMediaItemIndex].mediaID}.mp4",fileDir)) Uri.fromFile(File(fileDir,"${ _binding.vm!!.currentPlaylistData.value!!.songs[ _binding.vm!!.exoPlayer!!.currentMediaItemIndex].mediaID}.mp4"))
                    else Uri.parse( _binding.vm!!.currentPlaylistData.value!!.songs[ _binding.vm!!.exoPlayer!!.currentMediaItemIndex].videoURL)
                videoExoplayer.addMediaItem(MediaItem.fromUri(uri))
                videoExoplayer.seekTo( _binding.vm!!.exoPlayer!!.currentPosition)
                videoExoplayer.prepare()
                videoExoplayer.play()
            }
        }
    }
    fun changeInExoPos(position: Int){
        if( _binding.vm!!.exoPlayer != null) {
            if( _binding.vm!!.exoPlayer!!.currentMediaItemIndex < position)  _binding.vm!!.exoPlayer!!.previo)
            else  _binding.vm!!.exoPlayer!!.next()
            expanded_bottom_sheet_exo_player_img_view_pager.currentItem =  _binding.vm!!.exoPlayer!!.currentMediaItemIndex
            update()
        }
    }
}