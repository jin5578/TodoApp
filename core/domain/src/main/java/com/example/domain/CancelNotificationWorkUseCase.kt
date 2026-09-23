package com.example.domain

import androidx.work.WorkManager
import javax.inject.Inject

class CancelNotificationWorkUseCase
@Inject
constructor(
    private val workManager: WorkManager,
) {
    operator fun invoke(id: String) = workManager.cancelAllWorkByTag(tag = id)
}
