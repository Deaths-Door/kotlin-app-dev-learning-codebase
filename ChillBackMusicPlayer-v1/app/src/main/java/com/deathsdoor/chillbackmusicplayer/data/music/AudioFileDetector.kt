package com.deathsdoor.chillbackmusicplayer.data.music
import android.content.Context
import android.net.Uri
import android.provider.MediaStore

class AudioFileDetector(private var context: Context, var fileName: String) {
    companion object{
        private val columnToExtract = arrayOf(MediaStore.Audio.Media.DATA)
        private val externalFileURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        private val internalFileURI = MediaStore.Audio.Media.INTERNAL_CONTENT_URI

        private fun detectAudioFiles(context: Context,fileName: String?,contentURI: Uri):List<String>{
            val data = arrayListOf<String>()
            context.contentResolver.query(contentURI, columnToExtract, null, if(fileName == null) null else arrayOf(fileName, "audio/mpeg"), null)
                ?.use{
                    if(!it.moveToNext()) return data
                    do{ data.add(it.getString(it.getColumnIndexOrThrow(columnToExtract[0]))) }
                    while (it.moveToNext())
                }
            return data
        }

        fun detectJederAudioFileOnExternalStorage(context: Context): List<String> {
            return detectAudioFiles(context,null, externalFileURI)
        }
    }

}