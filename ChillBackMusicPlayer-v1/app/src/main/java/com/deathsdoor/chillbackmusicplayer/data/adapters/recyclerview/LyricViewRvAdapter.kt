package com.deathsdoor.chillbackmusicplayer.data.adapters.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.customviews.Lyrics
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.customviews.SongLyrics
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.int
import com.deathsdoor.chillbackmusicplayer.databinding.ItemLyricBinding
import com.deathsdoor.chillbackmusicplayer.ui.customviews.LyricsView
import com.masoudss.lib.WaveformSeekBar
import kotlin.math.roundToInt

//TODO extract info from bundle
class LyricViewRvAdapter(val data: SongLyrics,var bundle:Bundle) : RecyclerView.Adapter<LyricViewRvAdapter.ViewHolder>() {
    inner class ViewHolder(private val _binding: ItemLyricBinding) : RecyclerView.ViewHolder(_binding.root) {
        private val textSize = bundle.getFloat("textSize")
        private val textColor = bundle.getInt("textColor")
        private val textPadding = bundle.getFloat("textPadding")
        private val textPaddingStart = bundle.getFloat("textPaddingStart")
        private val textPaddingEnd = bundle.getFloat("textPaddingEnd")
        private val textPaddingTop = bundle.getFloat("textPaddingTop")
        private val textPaddingBottom = bundle.getFloat("textPaddingBottom")
        private val textAlignment =  bundle.getInt("textAlignment")

        //TODO implement this
        private val textLineExtraSpacing = bundle.getFloat("textLineExtraSpacing")
        private val textHyphenationFrequency = LyricsView.HyphenationFrequency.values().first { it.id == bundle.getInt("textHyphenationFrequency") }
        private val highlightSungLyrics = bundle.getBoolean("highlightSungLyrics")
        private val highlightLyricsColor = bundle.getInt("highlightLyricsColor")
        private val highlightDuration = bundle.getInt("highlightDuration")
        private val shadowColor = bundle.getInt("shadowColor")
        private val shadowRadius = bundle.getInt("shadowRadius")

        fun bind(lyrics: Lyrics) {
            _binding.root.text = lyrics.text

            _binding.root.textSize = textSize
            _binding.root.setTextColor(textColor)

            lyrics.color?.let { _binding.root.setTextColor(it) }

            val padding = textPadding.int()
            _binding.root.setPadding(padding,padding,padding,padding)
            _binding.root.setPadding(textPaddingStart.int(),padding,padding,padding)
            _binding.root.setPadding(padding,textPaddingTop.int(),padding,padding)
            _binding.root.setPadding(padding,padding,textPaddingEnd.int(),padding)
            _binding.root.setPadding(padding,padding,padding,textPaddingBottom.int())

            _binding.root.textAlignment = textAlignment

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricViewRvAdapter.ViewHolder
        = ViewHolder(ItemLyricBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    override fun onBindViewHolder(holder: LyricViewRvAdapter.ViewHolder, position: Int)
        = holder.bind(data.lyrics[position])
    override fun getItemCount(): Int = data.lyrics.size

}
