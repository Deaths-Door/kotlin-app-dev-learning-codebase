package com.deathsdoor.chillbackmusicplayer.data.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.customviews.Lyrics
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.customviews.SongLyrics
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.color
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.formatMinuteSecondMilliSecond
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.resetColor
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.ClickListeners.onClick
import com.deathsdoor.chillbackmusicplayer.databinding.ItemLyricEditorBinding
import com.google.android.exoplayer2.ExoPlayer

//TODO make adapter
class LyricEditorRvAdapter(private val context: Context,private val songLyrics:SongLyrics,private val exoplayer:ExoPlayer) : RecyclerView.Adapter<LyricEditorRvAdapter.Default>(){
    lateinit var clickListener:OnItemClickListener
    interface OnItemClickListener{
        fun onItemClicked(lyrics: Lyrics)
        fun onTimeStampClicked(lyrics: Lyrics, position: Int)
        fun onLyricClicked(lyrics: Lyrics)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricEditorRvAdapter.Default
        = Default(ItemLyricEditorBinding.inflate(LayoutInflater.from(parent.context),parent,false),clickListener)
    override fun onBindViewHolder(holder: LyricEditorRvAdapter.Default, position: Int) {
        holder.bind(songLyrics.lyrics[position],position)
    }
    override fun getItemCount(): Int = songLyrics.lyrics.size
    inner class Default(private val _binding: ItemLyricEditorBinding, private val clickListener: OnItemClickListener):RecyclerView.ViewHolder(_binding.root){
        fun bind(lyrics: Lyrics, position: Int) {
            if(position >= songLyrics.lyrics.size) return

            if(exoplayer.currentPosition >= lyrics.timeStamp && exoplayer.currentPosition <= songLyrics.lyrics[position + 1].timeStamp) _binding.root.setBackgroundColor(context.color(R.color.hell_purple))
            else _binding.root.resetColor()

            _binding.timestamp.text = lyrics.timeStamp.formatMinuteSecondMilliSecond()
            _binding.lyrics.text = lyrics.text
        }
        init {
            _binding.root.onClick {
                clickListener.onItemClicked(songLyrics.lyrics[bindingAdapterPosition])
            }
            _binding.timestamp.onClick {
                clickListener.onTimeStampClicked(songLyrics.lyrics[bindingAdapterPosition],bindingAdapterPosition)
            }
            _binding.lyrics.onClick {
                clickListener.onLyricClicked(songLyrics.lyrics[bindingAdapterPosition])
            }
        }
        //TODO do it
    }
}