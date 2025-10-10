package com.androidengineer.location

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class LocationPermissionHandler @Inject constructor(
    private val application: Application
) : DefaultLifecycleObserver {

    private var settingsLauncher: ActivityResultLauncher<Intent>? = null
    private var permissionLauncher: ActivityResultLauncher<String>? = null
    private var hostActivity: ComponentActivity? = null
    private var onPermissionResult: ((Boolean) -> Unit)? = null

    fun register(activity: ComponentActivity) {
        hostActivity = activity

        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                onPermissionResult?.invoke(true)
            } else {
                onPermissionResult?.invoke(false)
            }
        }

        settingsLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val granted = isPermissionGranted()
            onPermissionResult?.invoke(granted)
        }

        activity.lifecycle.addObserver(this)
    }

    fun checkPermission(onResult: (Boolean) -> Unit) {
        onPermissionResult = onResult
        val granted = isPermissionGranted()

        if (granted) {
            onResult(true)
        } else {
            permissionLauncher?.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    fun openSettings(onResult: (Boolean) -> Unit) {
        onPermissionResult = onResult
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", application.packageName, null)
        }
        settingsLauncher?.launch(intent)
    }

    private fun isPermissionGranted(): Boolean {
        val activity = hostActivity ?: return false
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        hostActivity = null
        settingsLauncher = null
        permissionLauncher = null
        onPermissionResult = null
    }
}