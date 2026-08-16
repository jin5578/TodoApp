package com.example.utils

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val notificationHelper: NotificationHelper
) : Worker(context, params) {
    override fun doWork(): Result {
        val id = inputData.getString(key = ID)
        val title = inputData.getString(key = TITLE)
        val time = inputData.getString(key = TIME)

        if (id != null && title != null && time != null) {
            notificationHelper.showNotification(id, title, time)
            return Result.success()
        }
        return Result.failure()
    }

    companion object {
        private const val ID = "id"
        private const val TITLE = "title"
        private const val TIME = "time"
    }
}