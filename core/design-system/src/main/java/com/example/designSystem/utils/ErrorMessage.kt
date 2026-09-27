package com.example.designSystem.utils

import android.content.res.Resources
import java.net.UnknownHostException
import com.example.designSystem.R as DesignSystemR

fun Throwable?.toErrorMessage(resources: Resources): String = when (this) {
    is UnknownHostException -> resources.getString(DesignSystemR.string.error_message_unknown)
    else -> resources.getString(DesignSystemR.string.error_message_network)
}
