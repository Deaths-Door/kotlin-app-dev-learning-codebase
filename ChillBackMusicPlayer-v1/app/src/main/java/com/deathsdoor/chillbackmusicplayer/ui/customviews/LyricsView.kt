package com.deathsdoor.chillbackmusicplayer.ui.customviews

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.ViewDebug
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.adapters.recyclerview.LyricViewRvAdapter
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.customviews.Lyrics
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.customviews.SongLyrics
import com.deathsdoor.chillbackmusicplayer.data.music.MusixSpiele
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.material.card.MaterialCardView
import org.checkerframework.checker.units.qual.Mass

class LyricsView(context:Context,attrs: AttributeSet? = null) : MaterialCardView(context,attrs) {
    constructor(context: Context) : this(context,null)

    private val recyclerView: RecyclerView
    private val bundle = Bundle()
    private var currentQueueLyrics : ArrayList<SongLyrics> = arrayListOf()
        get() {
            if(field.size == 0) return arrayListOf(SongLyrics(listOf(Lyrics(0,"Add Some Lyrics"))))
            return field
        }

    private var currentMediaItemIndex = 0
        set(value) {
            field = value
            recyclerView.adapter = LyricViewRvAdapter(currentQueueLyrics[currentMediaItemIndex],bundle)
        }

    enum class HyphenationFrequency(val id:Int){
        ALWAYS(0),
        NEVER(1),
        WHEN_NEEDED(2),
    }

    enum class TextAlignment(val id:Int){
        INHERIT(0),
        START(2),
        END(3),
        CENTER(4),
    }

    var backgroundColorView = Color.BLACK
        set(value) {
            field = value
            invalidate()
        }

    var textSize = 16f
        set(value) {
            field = value
            invalidate()
        }
    var textColor = Color.WHITE
        set(value) {
            field = value
            invalidate()
        }

    var textPadding = 0f
        set(value) {
            field = value
            invalidate()
        }
    var textPaddingStart = textPadding
        set(value) {
            field = value
            invalidate()
        }
    var textPaddingEnd = textPadding
        set(value) {
            field = value
            invalidate()
        }
    var textPaddingTop = textPadding
        set(value) {
            field = value
            invalidate()
        }
    var textPaddingBottom = textPadding
        set(value) {
            field = value
            invalidate()
        }

    var textAlignment = TextAlignment.INHERIT
        set(value) {
            field = value
            invalidate()
        }

    var textLineExtraSpacing = 0f
        set(value) {
            field = value
            invalidate()
        }

    var textHyphenationFrequency = HyphenationFrequency.WHEN_NEEDED
        set(value) {
            field = value
            invalidate()
        }
    var highlightSungLyrics = false
        set(value) {
            field = value
            invalidate()
        }
    var highlightLyricsColor = Color.WHITE
        set(value) {
            field = value
            invalidate()
        }

    var highlightDuration = -1
        set(value) {
            field = value
            invalidate()
        }
    var shadowColor: Int? = null
        set(value) {
            field = value
            invalidate()
        }
    var shadowRadius :Int? = null
        set(value) {
            field = value
            invalidate()
        }

    init {
        //default recycler view
        recyclerView = RecyclerView(context)
        recyclerView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        addView(recyclerView)
        currentMediaItemIndex = 0

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LyricsView)
        backgroundColorView = typedArray.getColor(R.styleable.LyricsView_backgroundColor,backgroundColorView)

        textSize =  typedArray.getDimension(R.styleable.LyricsView_textSize,textSize)
        textColor = typedArray.getColor(R.styleable.LyricsView_textColor,textColor)
        textPadding =  typedArray.getDimension(R.styleable.LyricsView_textPadding,textPadding)

        textPaddingStart =  typedArray.getDimension(R.styleable.LyricsView_textPaddingStart,textPaddingStart)
        textPaddingEnd =  typedArray.getDimension(R.styleable.LyricsView_textPaddingEnd,textPaddingEnd)
        textPaddingTop =  typedArray.getDimension(R.styleable.LyricsView_textPaddingTop,textPaddingTop)
        textPaddingBottom =  typedArray.getDimension(R.styleable.LyricsView_textPaddingBottom,textPaddingBottom)

        val textAlignmentValue = typedArray.getInt(R.styleable.LyricsView_textAlignment, TextAlignment.INHERIT.id)
        textAlignment = when(textAlignmentValue){
            TextAlignment.INHERIT.id -> TextAlignment.INHERIT
            TextAlignment.START.id  -> TextAlignment.START
            TextAlignment.END.id  -> TextAlignment.END
            TextAlignment.CENTER.id  -> TextAlignment.CENTER
            else -> throw IllegalArgumentException("$textAlignmentValue is not a valid value for property textAlignment")
        }

        textLineExtraSpacing =  typedArray.getDimension(R.styleable.LyricsView_textLineExtraSpacing,textLineExtraSpacing)


        val texthyphenationFrequency = typedArray.getInt(R.styleable.LyricsView_textHyphenationFrequency, HyphenationFrequency.WHEN_NEEDED.id)
        textHyphenationFrequency = when(texthyphenationFrequency){
            0 -> HyphenationFrequency.ALWAYS
            1 -> HyphenationFrequency.NEVER
            2 -> HyphenationFrequency.WHEN_NEEDED
            else -> throw IllegalArgumentException("$textAlignmentValue is not a valid value for property textHyphenationFrequency")
        }

        highlightSungLyrics =  typedArray.getBoolean(R.styleable.LyricsView_highlightSungLyrics,highlightSungLyrics)
        highlightLyricsColor =  typedArray.getColor(R.styleable.LyricsView_highlightLyricsColor,highlightLyricsColor)

        highlightDuration = typedArray.getInt(R.styleable.LyricsView_highlightDuration, highlightDuration)

        shadowColor = shadowColor?.let { typedArray.getColor(R.styleable.LyricsView_shadowColor, it) }
        shadowRadius = shadowRadius?.let { typedArray.getColor(R.styleable.LyricsView_shadowRadius, it) }

        typedArray.recycle()

        this.setBackgroundColor(backgroundColorView)


        bundle.putFloat("textSize", textSize)
        bundle.putInt("textColor", textColor)
        bundle.putFloat("textPadding", textPadding)
        bundle.putFloat("textPaddingStart", textPaddingStart)
        bundle.putFloat("textPaddingEnd", textPaddingEnd)
        bundle.putFloat("textPaddingTop", textPaddingTop)
        bundle.putFloat("textPaddingBottom", textPaddingBottom)
        bundle.putInt("textAlignment", textAlignment.id)
        bundle.putFloat("textLineExtraSpacing", textLineExtraSpacing)
        bundle.putInt("textHyphenationFrequency", textHyphenationFrequency.id)
        bundle.putBoolean("highlightSungLyrics", highlightSungLyrics)
        bundle.putInt("highlightLyricsColor", highlightLyricsColor)
        bundle.putInt("highlightDuration", highlightDuration)

        shadowColor?.let {  bundle.putInt("shadowColor",it) }
        shadowRadius?.let {  bundle.putInt("shadowRadius",it) }

    }

    fun addSongLyric(data:SongLyrics) {
        if(currentQueueLyrics.size == 1) currentQueueLyrics.clear()
        currentQueueLyrics.add(data)
    }
    fun removeSongLyrics(index:Int) = currentQueueLyrics.removeAt(index)
    fun removeAllSongLyrics() = currentQueueLyrics.clear()
    fun getSongLyric(index: Int) = currentQueueLyrics[index]
    fun getAllSongLyrics() = currentQueueLyrics
    fun setCurrentLyrics(index:Int){ currentMediaItemIndex = index }
    fun nextSongLyrics(){ currentMediaItemIndex += 1 }
    fun previousSongLyrics(){ currentMediaItemIndex -= 1 }

    fun bindWithExoPlayer(exoPlayer: ExoPlayer){
        Handler(Looper.getMainLooper()).postDelayed({
            if(exoPlayer.currentMediaItemIndex != currentMediaItemIndex) currentMediaItemIndex = exoPlayer.currentMediaItemIndex
            val result = currentQueueLyrics[currentMediaItemIndex].lyrics.binarySearch {
                val comparison = exoPlayer.currentPosition.compareTo(it.timeStamp)
                when {
                    comparison == 0 -> 0
                    comparison > 0 -> 1
                    else -> -1
                }
            }
            recyclerView.scrollToPosition(result)
        },100)
    }

    //TODO doesnt work
    @Deprecated("doesnt work listener not triggering when needed")
    fun bindWithExoplayer(exoPlayer: ExoPlayer){
        MusixSpiele.addListener(exoPlayer,object :MusixSpiele.OnPlayerStateChange{
            override fun onSeekedToDifferentMediaItem() {
                super.onSeekedToDifferentMediaItem()
                currentMediaItemIndex = exoPlayer.currentMediaItemIndex
            }

            //TODO is triggered only when user changes positon
            override fun onCurrentPositionChanged(currentPosition: Long) {
                super.onCurrentPositionChanged(currentPosition)
                val result = currentQueueLyrics[currentMediaItemIndex].lyrics.binarySearch {
                    val comparison = currentPosition.compareTo(it.timeStamp)
                    when {
                        comparison == 0 -> 0
                        comparison > 0 -> 1
                        else -> -1
                    }
                }
                recyclerView.scrollToPosition(result)
            }
        })
    }
}