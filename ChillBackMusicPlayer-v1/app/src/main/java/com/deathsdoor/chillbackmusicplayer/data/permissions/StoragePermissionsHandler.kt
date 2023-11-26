package com.deathsdoor.chillbackmusicplayer.data.permissions

import android.Manifest
import android.app.Activity
import com.deathsdoor.chillbackmusicplayer.data.randomNumber

//TODO add all storae permisisons : ACCESS_MEDIA_LOCATION / MANAGE_DOCUMENTS
class StoragePermissionsHandler(
    activity: Activity,
    requestCode: Int = randomNumber,
    readExternalStorage: Boolean = false,
    writeExternalStorage: Boolean = false,
    ):PermissionHandler(activity,requestCode, createPermissionsArray(readExternalStorage,writeExternalStorage)){
        companion object{
            private fun createPermissionsArray(
                readExternalStorage: Boolean,
                writeExternalStorage: Boolean
            ): Array<String> {
                val permissions = arrayListOf<String>()
                when{
                    readExternalStorage -> permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
                    writeExternalStorage -> permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                return permissions.toTypedArray()
            }
        }
    }