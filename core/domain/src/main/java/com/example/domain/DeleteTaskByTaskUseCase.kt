package com.example.domain

import com.example.dataApi.repository.TaskRepository
import com.example.model.Task
import javax.inject.Inject

class DeleteTaskByTaskUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
    private val cancelNotificationWorkUseCase: CancelNotificationWorkUseCase,
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.deleteTaskByTask(task = task)
        cancelNotificationWorkUseCase(id = task.uuid)
    }
}
