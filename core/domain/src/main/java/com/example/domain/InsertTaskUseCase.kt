package com.example.domain

import com.example.data_api.repository.TaskRepository
import com.example.model.Task
import javax.inject.Inject

class InsertTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val scheduleNotificationWorkUseCase: ScheduleNotificationWorkUseCase,
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.insertTask(task = task)
        if (task.reminderTime != null) {
            scheduleNotificationWorkUseCase(task = task)
        }
    }
}