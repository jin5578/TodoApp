package com.example.domain

import com.example.dataApi.repository.TaskRepository
import com.example.model.Task
import javax.inject.Inject

class UpdateTaskUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
    private val scheduleNotificationWorkUseCase: ScheduleNotificationWorkUseCase,
    private val cancelNotificationWorkUseCase: CancelNotificationWorkUseCase,
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.updateTask(task)

        cancelNotificationWorkUseCase(id = task.uuid)

        if (task.reminderTime != null && !task.isCompleted) {
            scheduleNotificationWorkUseCase(task = task)
        }
    }
}
