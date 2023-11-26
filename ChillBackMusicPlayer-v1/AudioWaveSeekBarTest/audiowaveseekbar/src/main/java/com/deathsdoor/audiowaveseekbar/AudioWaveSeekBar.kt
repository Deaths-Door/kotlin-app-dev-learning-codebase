package com.deathsdoor.audiowaveseekbar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewConfiguration

class AudioWaveSeekBar(context: Context, attrs: AttributeSet? = null,) : View(context,attrs){
    private var mCanvasWidth = 0
    private var mCanvasHeight = 0
    private val mWavePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mWaveRect = RectF()
    private val mMarkerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mMarkerRect = RectF()
    private val mProgressCanvas = Canvas()
    private var mTouchDownX = 0F
    private var mProgress = 0f
    private var mScaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var mAlreadyMoved = false
    private lateinit var progressBitmap: Bitmap
    private lateinit var progressShader: Shader

    interface OnProgressChanged{
        fun onProgressChanged(audioWaveSeekBar: AudioWaveSeekBar,oldProgress:Float, newProgress: Float)
    }

    var audio:IntArray
        set(value) {
            field = value
            invalidate()
        }

    var onProgressChangedListener = object :OnProgressChanged{
        override fun onProgressChanged(audioWaveSeekBar: AudioWaveSeekBar, oldProgress: Float, newProgress: Float) {}
    }

    var currentProgress:Float = 0F
        set(value) {
            onProgressChangedListener.onProgressChanged(this,field,value)
            field = value
            invalidate()
        }

    var maxProgress:Int = audio.max()
        set(value) {
            field = value
            invalidate()
        }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (audio.isEmpty()) return

        canvas.clipRect(
            paddingLeft,
            paddingTop,
            mCanvasWidth - paddingRight,
            mCanvasHeight - paddingBottom
        )
        val totalWaveWidth = waveGap + waveWidth
        var step = audio.size / (getAvailableWidth() / totalWaveWidth)

        var previousWaveRight = paddingLeft.toFloat() + wavePaddingLeft
        var sampleItemPosition: Int

        val barsToDraw = (getAvailableWidth() / totalWaveWidth).toInt()
        val start: Int
        val progressXPosition: Float
        if (visibleProgress > 0) {
            // If visibleProgress is > 0, the bars move instead of the blue colored part
            step *= visibleProgress / maxProgress
            val barsForProgress = barsToDraw + 1
            // intFactor is required as depending on whether an equal number of bars must be drawn, the start will switch differently
            val intFactor = (((barsForProgress + 1) % 2))
            // Calculate fixed start change depending
            previousWaveRight += (getAvailableWidth() * 0.5F) % totalWaveWidth
            previousWaveRight += intFactor * 0.5F * totalWaveWidth - totalWaveWidth
            // Calculate start change depending on progress, so that it moves smoothly
            previousWaveRight -= ((progress + intFactor * visibleProgress / barsForProgress * 0.5f) % (visibleProgress / barsForProgress)) / (visibleProgress / barsForProgress) * totalWaveWidth
            start =
                (progress * barsForProgress / visibleProgress - (barsForProgress / 2F)).roundToInt() - 1
            progressXPosition = getAvailableWidth() * 0.5F
        } else {
            start = 0
            progressXPosition = getAvailableWidth() * progress / maxProgress
        }

        // draw waves
        for (i in start until barsToDraw + start + 3) {
            sampleItemPosition = floor(i * step).roundToInt()
            var waveHeight =
                if (sampleItemPosition in audio.indices && maxProgress != 0)
                    (getAvailableHeight() - wavePaddingTop - wavePaddingBottom) * (audio[sampleItemPosition].toFloat() / maxProgress)
                else 0F

            if (waveHeight < waveMinHeight) waveHeight = waveMinHeight

            val top: Float = when (waveGravity) {
                WaveGravity.TOP -> paddingTop.toFloat() + wavePaddingTop
                WaveGravity.CENTER -> (paddingTop + wavePaddingTop + getAvailableHeight()) / 2F - waveHeight / 2F
                WaveGravity.BOTTOM -> mCanvasHeight - paddingBottom - wavePaddingBottom - waveHeight
            }

            mWaveRect.set(
                previousWaveRight,
                top,
                previousWaveRight + waveWidth,
                top + waveHeight
            )
            when {
                // if progress is currently in waveRect, color have to be split up
                mWaveRect.contains(progressXPosition, mWaveRect.centerY()) -> {
                    mProgressCanvas.setBitmap(progressBitmap)
                    mWavePaint.color = waveProgressColor
                    mProgressCanvas.drawRect(
                        0F,
                        0F,
                        progressXPosition,
                        mWaveRect.bottom,
                        mWavePaint
                    )
                    mWavePaint.color = waveBackgroundColor
                    mProgressCanvas.drawRect(
                        progressXPosition,
                        0F,
                        getAvailableWidth().toFloat(),
                        mWaveRect.bottom,
                        mWavePaint
                    )
                    mWavePaint.shader = progressShader
                }
                mWaveRect.right <= progressXPosition -> {
                    mWavePaint.color = waveProgressColor
                    mWavePaint.shader = null
                }
                else -> {
                    mWavePaint.color = waveBackgroundColor
                    mWavePaint.shader = null
                }
            }
            canvas.drawRoundRect(mWaveRect, waveCornerRadius, waveCornerRadius, mWavePaint)
            previousWaveRight = mWaveRect.right + waveGap
        }

    }
}