package com.example.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.theme.TodoTheme
import com.example.main.navigation.rememberMainNavigator
import com.example.model.ThemeType
import com.example.utils.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var notificationHelper: NotificationHelper

    private val viewModel by viewModels<MainViewModel>()

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setUpNotification()

        setContent {
            val themeType by viewModel.themeType.collectAsStateWithLifecycle(
                initialValue = ThemeType.SYSTEM,
                lifecycleOwner = this,
            )

            val navigator = rememberMainNavigator()

            TodoTheme(theme = themeType) {
                MainRoute(navigator = navigator)
            }
        }
    }

    private fun setUpNotification() {
        notificationHelper.createChannel()
        notificationHelper.cancelAll()
        requestNotificationPermissionIfNeeded()
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
