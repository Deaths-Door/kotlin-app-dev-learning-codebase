package com.deathsdoor.chillbackmusicplayer.data.extensions.export

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.math.abs
import kotlin.math.max

//TODO("use this to record me and use shazam api to recongize the song")
class AudioRecorder {
    private var audioRecord: AudioRecord? = null
    private var recordingBufferSize = 0
    private var outputFile: String = ""
    private var buffer = ByteArray(recordingBufferSize)
    private val sampleRate = 44100
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO

    private var isRecording = false
    private var isPaused = false

    private var startTime = 0L
    //TODO update pausedTime
    private var pausedTime = 0L

    private val handlerCurrentSessionRecordingTime = Handler(Looper.getMainLooper())
    private val runnableCurrentSessionRecordingTime = Runnable { functionCurrentSessionRecordingTime }
    private val functionCurrentSessionRecordingTime = { currentSessionRecordingTime++ }

    private val handlerPausedTime = Handler(Looper.getMainLooper())
    private val runnablePausedTime  = Runnable { functionPausedTime }
    private val functionPausedTime  = { pausedTime++ }

    var audioSource = MediaRecorder.AudioSource.MIC
    var audioFormat = AudioFormat.ENCODING_PCM_16BIT

    var maxRecordingTime = 0L

    val totalRecordingTime : Long get() = System.currentTimeMillis() - startTime - pausedTime

    var currentSessionRecordingTime:Long = 0

    var amplitudeChangeListener = object : OnAmplitudeChangeListener {
        override fun onAmplitudeChanged(amplitude: Int) {
            Log.d("Audio Recorder : ","Amplitude Changed to $amplitude")
        }
    }
    var onNotifyListener = object :onNotify{
        override fun onFinished() {
            Log.d("Audio Recorder : ","Audio Record Finished")
        }

        override fun onError(errorCode: Int) {
            Log.e("Audio Recorder : ","Error of AudioRecord : $errorCode")
        }
    }

    interface OnAmplitudeChangeListener {
        fun onAmplitudeChanged(amplitude: Int)
    }

    interface onNotify{
        fun onFinished()
        fun onError(errorCode:Int)
    }

    @SuppressLint("MissingPermission")
    fun start(filePath: String) {
        try {
            recordingBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

            audioRecord = AudioRecord.Builder()
                .setAudioSource(audioSource)
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setSampleRate(sampleRate)
                        .setEncoding(audioFormat)
                        .setChannelMask(channelConfig)
                        .build()
                )
                .setBufferSizeInBytes(recordingBufferSize)
                .build()
            outputFile = filePath

            Thread{
                val startTime = System.currentTimeMillis()
                while (isRecording) {
                    if (maxRecordingTime > 0 && System.currentTimeMillis() - startTime >= maxRecordingTime) stop()
                }
            }.start()


            startTime = System.currentTimeMillis()
            audioRecord?.startRecording()
            isRecording = true
            pausedTime = 0

            pausedTime = 0
            handlerCurrentSessionRecordingTime.postDelayed(runnableCurrentSessionRecordingTime,1)
            handlerPausedTime.removeCallbacks(runnablePausedTime)

            Thread {
                writeAudioDataToFile()
            }.start()
        }
        catch (e:Exception){
            onNotifyListener.onError(AudioRecord.ERROR)
            stop()
        }
    }

    fun stop() {
        isRecording = false

        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null

        handlerCurrentSessionRecordingTime.removeCallbacks(runnableCurrentSessionRecordingTime)
        handlerPausedTime.postDelayed(runnablePausedTime,1)

        onNotifyListener.onFinished()
    }

    fun pause(){
        if(!isRecording && isPaused) return

        audioRecord?.stop()
        isPaused = true

        handlerCurrentSessionRecordingTime.removeCallbacks(runnableCurrentSessionRecordingTime)
        handlerPausedTime.postDelayed(runnablePausedTime,1)
    }

    fun resume(){
        if(!isRecording && !isPaused) return

        pausedTime = 0
        handlerCurrentSessionRecordingTime.postDelayed(runnableCurrentSessionRecordingTime,1)
        handlerPausedTime.removeCallbacks(runnablePausedTime)

        audioRecord?.startRecording()
        isPaused = false
        Thread { writeAudioDataToFile() }.start()
    }

    private fun writeAudioDataToFile() {
        val dataOutputStream = DataOutputStream(FileOutputStream(outputFile))
        while (isRecording) {
            val numberOfShort = audioRecord?.read(buffer, 0, buffer.size)
            var maxAmplitude = 0
            for (i in 0 until numberOfShort!!) {
                maxAmplitude = max(maxAmplitude, abs(buffer[i].toInt()))
            }
            if (maxAmplitude > 0) amplitudeChangeListener.onAmplitudeChanged(maxAmplitude)
        }
        dataOutputStream.close()
    }
    fun deleteFile() = File(outputFile).delete()
}