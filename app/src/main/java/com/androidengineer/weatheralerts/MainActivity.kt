package com.androidengineer.weatheralerts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowInsetsControllerCompat
import com.androidengineer.location.LocationPermissionHandler
import com.androidengineer.theme.LocalThemeColors
import com.androidengineer.theme.WeatherAppTheme
import com.androidengineer.utils.MessageDialoge
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var locationPermissionHandler: LocationPermissionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationPermissionHandler.register(this)

        checkPermissionFlow()
    }

    private fun checkPermissionFlow() {
        locationPermissionHandler.checkPermission { granted ->
            if (granted) {
                showMainContent()
            } else {
                showPermissionDialog()
            }
        }
    }

    private fun showPermissionDialog() {
        setContent {
            MessageDialoge(
                message = stringResource(R.string.grant_permission_message),
                ok = {
                    locationPermissionHandler.openSettings { granted ->
                        if (granted) showMainContent()
                        else showPermissionDialog()
                    }
                },
                cancel = {
                    finishAffinity()
                },
                okButtonText = stringResource(R.string.open_settings_title),
                cancelButtonText = stringResource(R.string.close_app_title)
            )
        }
    }

    private fun showMainContent() {
        setContent {
            WeatherAppTheme {
                val color = LocalThemeColors.current
                SideEffect {
                    window.statusBarColor = Color(color.appBackgroundGradientStart.toArgb()).toArgb()
                    WindowInsetsControllerCompat(
                        window,
                        window.decorView
                    ).isAppearanceLightStatusBars = true
                }
                MainNavigation()
            }
        }
    }
}