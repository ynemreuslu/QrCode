package com.ynemreuslu.instantqr

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.ynemreuslu.instantqr.navigaiton.MainScreen
import com.ynemreuslu.instantqr.ui.theme.InstantQrTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()),
        )

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            InstantQrTheme {
                val navController = rememberNavController()
                MainScreen(navController)

                val shortcutType = intent.getStringExtra("shortcut_type")
                when (shortcutType) {
                    "scanner" -> {
                        navController.navigate("scanner")
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}