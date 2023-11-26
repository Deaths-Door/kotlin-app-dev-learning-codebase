package com.deathsdoor.chillbackmusicplayer.data.extensions

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.deathsdoor.chillbackmusicplayer.data.Constants
import java.io.File

@Deprecated("use files in export to do this")
object File {
    private fun Context.canonicalFileDir(): File = this.filesDir.canonicalFile

    fun String.internalFile(context: Context) : File {
        val file =  File(File(context.canonicalFileDir(), ""),this)
        file.parentFile?.mkdirs()
        return file
    }


    @Deprecated("use files in export to do this")
    fun String.internalFile(context: Context,fileExtension:Constants.FILE_EXTENSIONS? = null) : File {
        return if(fileExtension?.extension == null) File(File(context.canonicalFileDir(), ""),this)
        else File(File(context.canonicalFileDir(), ""),this + fileExtension.extension)
    }
    @Deprecated("use files in export to do this")
    @Suppress("NOTHING_TO_INLINE")
    inline fun String.internalFileExists(context: Context,fileExtension:Constants.FILE_EXTENSIONS? = null): Boolean = internalFile(context,fileExtension).exists()

    /*fun String.externalFile(fileExtension:Contants.FILE_EXTENSIONS? = null):File {
        return if(fileExtension?.extension == null) File(Environment.getExternalStorageDirectory().toString() + File.separator + this)
        else File(Environment.getExternalStorageDirectory().toString() + File.separator + this + fileExtension.extension)
    }
    fun String.externalFileExists(fileExtension:Contants.FILE_EXTENSIONS? = null):Boolean = this.externalFile(fileExtension).exists()*/

    @Deprecated("use files in export to do this")
    fun String.externalMusicFileUri(context: Context): Uri? {
        val projection = arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME)
        val selection = "${MediaStore.Audio.Media.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(this)
        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val idColumn = it.getColumnIndex(MediaStore.Audio.Media._ID)
                val id = it.getLong(idColumn)
                return Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id.toString())
            }
        }
        return null
    }


}