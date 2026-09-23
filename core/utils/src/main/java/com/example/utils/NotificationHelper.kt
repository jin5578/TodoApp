package com.example.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.example.design_system.R as DesignSystemR

class NotificationHelper
@Inject
constructor(
    @param:ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager,
) {
    fun createChannel() {
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH,
            )
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(
        id: String,
        title: String,
        time: String,
    ) {
        val intent =
            Intent(
                context.packageManager.getLaunchIntentForPackage(
                    context.packageName,
                ),
            ).apply {
                flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                System.currentTimeMillis().toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val defaultSoundUri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        fun baseBuilder() = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(DesignSystemR.drawable.svg_check_list)
            color =
                context.resources.getColor(DesignSystemR.color.black, null)
            setContentTitle(title)
            setContentText(time)
            setAutoCancel(true)
            setGroup(GROUP_KEY_BASIC)
            setContentIntent(pendingIntent)
        }

        val builder =
            baseBuilder().apply {
                setSound(defaultSoundUri)
            }

        val builderSummary =
            baseBuilder().apply {
                setOnlyAlertOnce(true)
                setGroupSummary(true)
            }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.apply {
                notify(System.currentTimeMillis().toInt(), builder.build())
                notify(BASIC_NOTIFICATION_ID, builderSummary.build())
            }
        }
    }

    fun cancelAll() {
        notificationManager.cancelAll()
    }

    companion object {
        private const val CHANNEL_ID = "todo-notification"
        private const val CHANNEL_NAME = "TODO Reminder"

        private const val GROUP_KEY_BASIC = "com.example.todo.BASIC"
        private const val BASIC_NOTIFICATION_ID = 0
    }
}
