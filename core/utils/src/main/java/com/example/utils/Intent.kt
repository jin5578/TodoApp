package com.example.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.net.toUri

fun openUrl(
    context: Context,
    url: String,
) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    context.startActivity(intent)
}

fun intentToSetting(context: Context) {
    val intent =
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = "package:${context.packageName}".toUri()
        }
    context.startActivity(intent, null)
}
