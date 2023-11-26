package com.deathsdoor.chillbackmusicplayer.data.extensions.export

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

@Deprecated("use the library")
class AudioFilesDetect(private var context: Context, var fileName: String? = null) {
    private val externalFileURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    private val internalFileURI = MediaStore.Audio.Media.INTERNAL_CONTENT_URI

    //TODO get file with specific name
    private val selectionFileWithSpecificName = "${MediaStore.Audio.Media.DISPLAY_NAME} = ? AND ${MediaStore.Audio.Media.MIME_TYPE} = ?"
    private val selectionFileExists = "${MediaStore.Audio.Media.DISPLAY_NAME} = ? AND ${MediaStore.Audio.Media.MIME_TYPE} = ?"

    private val column = MediaStore.Audio.Media.DATA
    private val columnsToExtract = arrayOf(column)
    val isInternalFile :Boolean get() = fileExists(internalFileURI)
    val isExternalFile :Boolean get() = fileExists(externalFileURI)
    val exists : Boolean
        get(){
            if(isInternalFile || isExternalFile) return true
            return false
        }

    private fun fileExists(contentURI: Uri): Boolean {
        val selectionArgs = arrayOf(fileName, "audio/mpeg")
        val cursor = context.contentResolver.query(contentURI, null, selectionFileExists, selectionArgs, null)
        cursor?.use {
            if(!it.moveToNext()) return false
            return true
        }
        return false
    }

    private fun extractFileMediaColumns(contentURI: Uri, action: (data: List<String>?) -> Unit): AudioFilesDetect {
        context.contentResolver.query(contentURI,columnsToExtract, null, if(fileName == null) null else arrayOf(fileName, "audio/mpeg"), null)
            ?.use {
                if(!it.moveToNext()) action(null)

                val list = arrayListOf<String>()
                do {
                    list.add(it.getString(it.getColumnIndexOrThrow(column)))
                }while(it.moveToNext())

                action(list)
            }
        return this
    }

    fun extractInternalFileMediaColumns(action: (data: List<String>?) -> Unit): AudioFilesDetect {
        return extractFileMediaColumns(internalFileURI,action)
    }
    fun extractExternalFileMediaColumns(action: (data: List<String>?) -> Unit): AudioFilesDetect {
        return extractFileMediaColumns(externalFileURI,action)
    }
}
