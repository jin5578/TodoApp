package com.example.domain

import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.model.Task
import com.example.utils.NotificationWorker
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScheduleNotificationWorkUseCase @Inject constructor(
    private val workManager: WorkManager,
) {
    operator fun invoke(task: Task) {
        val reminderDateTime = task.reminderTime ?: return

        val reminderDateTimeSec =
            reminderDateTime.toEpochSecond(ZoneOffset.UTC)

        val currentDateTimeSec =
            LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

        val delaySec = reminderDateTimeSec - currentDateTimeSec
        if (delaySec > 0) {
            val data = Data.Builder()
                .putString(ID, task.uuid)
                .putString(TITLE, task.title)
                .putString(TIME, task.time.toString())
                .build()
            val workRequest =
                OneTimeWorkRequestBuilder<NotificationWorker>()
                    .setInitialDelay(delaySec, TimeUnit.SECONDS)
                    .setInputData(data)
                    .addTag(task.uuid)
                    .build()
            workManager.enqueue(workRequest)
        }
    }

    companion object {
        private const val ID = "id"
        private const val TITLE = "title"
        private const val TIME = "time"
    }
}