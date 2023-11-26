package com.deathsdoor.chillback.common.data.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

internal actual object Permissions {
    const val accessLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
    const val readExternalStorage = android.Manifest.permission.READ_EXTERNAL_STORAGE
    fun canAccessLocation(context : Context) = ContextCompat.checkSelfPermission(context,accessLocation) == PackageManager.PERMISSION_GRANTED
    fun canReadExternalStorage(context : Context) = ContextCompat.checkSelfPermission(context,readExternalStorage) == PackageManager.PERMISSION_GRANTED
    fun canWriteExternalStorage(context : Context) = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
}