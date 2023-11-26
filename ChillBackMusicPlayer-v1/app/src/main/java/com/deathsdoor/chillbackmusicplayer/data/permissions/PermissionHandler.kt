package com.deathsdoor.chillbackmusicplayer.data.permissions
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlin.random.Random

//TODO finish and update it from the old oen
class PermissionHandler @Deprecated("use the thing from the thing library") constructor(val context: Context, val requestCode: Int = randomNumber, val permissions: Array<Permission>){
    enum class Permission(val permission: String) {
        READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE),
        WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
    companion object{
        private val randomNumber : Int get() = Random(System.currentTimeMillis()).nextInt()

        fun havePermission(context:Context,permission : Permission): Boolean {
            return ContextCompat.checkSelfPermission(context,permission.permission) == PackageManager.PERMISSION_GRANTED
        }

        @Composable
        @OptIn(ExperimentalPermissionsApi::class)
        fun RequestPermission(permission: Permission,onResult:(granted:Boolean)->Unit){
            val permissionState = rememberPermissionState(permission = permission.permission)
            val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = {
                onResult(it)
            })
            if(permissionState.status.isGranted) onResult(true)
            else if(permissionState.status.shouldShowRationale) {/*TODO show something*/}
            else LaunchedEffect(Unit){
                launcher.launch(permissionState.permission)
            }
        }
    }
}