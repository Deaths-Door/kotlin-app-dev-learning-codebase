package com.deathsdoor.chillbackmusicplayer.data.permissions

import android.Manifest
import android.app.Activity
import com.deathsdoor.chillbackmusicplayer.data.randomNumber

class LocationPermissionHandler(
    activity: Activity,
    requestCode: Int = randomNumber,
    accessCoarseLocation: Boolean = false,
    accessFineLocation: Boolean = false,
    accessBackgroundLocation: Boolean = false,
    accessExtraCommandsLocation: Boolean = false,
    ) : PermissionHandler(activity,requestCode,
            createPermissionsArray(accessCoarseLocation,accessFineLocation,accessBackgroundLocation,accessExtraCommandsLocation)
        ){

    companion object {
        private fun createPermissionsArray(
            accessCoarseLocation: Boolean,
            accessFineLocation: Boolean,
            accessBackgroundLocation: Boolean,
            accessExtraCommandsLocation: Boolean
        ): Array<String> {
            val permissions = arrayListOf<String>()
            when {
                accessCoarseLocation -> permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
                accessFineLocation -> permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
                accessBackgroundLocation -> permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                accessExtraCommandsLocation -> permissions.add("extra_permission")
            }
            return permissions.toTypedArray()
        }
    }

}