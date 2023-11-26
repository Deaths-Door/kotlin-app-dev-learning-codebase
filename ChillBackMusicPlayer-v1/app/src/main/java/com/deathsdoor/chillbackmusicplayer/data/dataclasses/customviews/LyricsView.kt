package com.deathsdoor.chillbackmusicplayer.data.dataclasses.customviews

import androidx.annotation.ColorInt
import com.deathsdoor.chillbackmusicplayer.data.extensions.debugLog
import java.io.File

data class SongLyrics(val lyrics:List<Lyrics>){
    constructor(file: File):this(parseLyricFile(file).lyrics)
    constructor(fileContent: String): this(parseLyricFile(fileContent).lyrics)

    private class InvalidFormatError(private val msg:String): Throwable() {
        override val message: String get() = "Invalid Lyric File Format : $msg"
    }

    fun asLrc(): String = lyrics.joinToString("\n") { lyrics -> "[${formatTimeStamp(lyrics.timeStamp)}] ${lyrics.text}" }

    companion object{
        fun generateLrcFileFormat(string:String): String {
            var x = 1
            val list = if(string.contains("\n")) string.split("\n") else string.split(".")
            return list.joinToString("\n") {
                if (!it.matches("\\[\\d{2}:\\d{2}:\\d{2}(?:\\.\\d{2,3})?]".toRegex())) "[0${x++}:00:00] $it" else it
            }
        }
        fun parseLyricFile(file: File): SongLyrics {
            if(!file.extension.equals("lrc", ignoreCase = true)) throw IllegalArgumentException("File has to be type LRC to parse it")

            var string = ""
            file.useLines { line ->
                line.forEach {
                    string += it + "\n"
                }
            }
            return parseLyricFile(string)
        }

        @Deprecated("update to improve it")
        fun parseLyricFile(fileContent: String): SongLyrics {
            if(fileContent.isEmpty()) throw InvalidFormatError("File Content Can't be empty")

            val lyricsList = arrayListOf<Lyrics>()
            fileContent.lines().forEach loop@{
                if (!it.startsWith("[") || it.elementAt(9) != ']') throw InvalidFormatError("Contains invalid brackets -> $it")
                val timeAndText = it.substring(1, it.length - 1).split("]")

                if (timeAndText.size != 2) throw InvalidFormatError("Invalid time -> $timeAndText")
                val time = parseTime(timeAndText[0]) ?: throw InvalidFormatError("Invalid time -> ${timeAndText[0]}")

                lyricsList.add(Lyrics(time, timeAndText[1]))
                "Lyrics : ".debugLog(lyricsList[lyricsList.lastIndex])
            }
            "Lyrics : ".debugLog(lyricsList)
            return SongLyrics(lyricsList)
        }
        private fun parseTime(timeString: String): Long? {
            try {
                val timeParts = timeString.split(":")
                when (timeParts.size) {
                    3 -> {
                        val minutes = timeParts[0].toLong()
                        val seconds = timeParts[1].toLong()
                        val milliseconds = timeParts[2].toLong()
                        return minutes * 60 * 1000 + seconds * 1000 + milliseconds
                    }
                    2 -> {
                        val minutes = timeParts[0].toLong()
                        val secondsAndMilliseconds = timeParts[1].split(".")
                        if (secondsAndMilliseconds.size != 2) {
                            return null
                        }
                        val seconds = secondsAndMilliseconds[0].toLong()
                        val milliseconds = secondsAndMilliseconds[1].toLong()
                        return minutes * 60 * 1000 + seconds * 1000 + milliseconds
                    }
                    else -> return null
                }
            } catch (e: NumberFormatException) {
                return null
            }
        }
        private fun formatTimeStamp(timeStamp: Long): String {
            val minutes = timeStamp / 1000 / 60
            val seconds = timeStamp / 1000 % 60
            val milliseconds = timeStamp % 1000 / 10
            return "${"%02d".format(minutes)}:${"%02d".format(seconds)}.${"%02d".format(milliseconds)}"
        }
    }
}
data class Lyrics(var timeStamp:Long, val text:String, @ColorInt val color: Int? = null)
