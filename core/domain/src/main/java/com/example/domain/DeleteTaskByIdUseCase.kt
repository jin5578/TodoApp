package com.example.domain

import com.example.dataApi.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskByIdUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
    private val cancelNotificationWorkUseCase: CancelNotificationWorkUseCase,
) {
    suspend operator fun invoke(
        id: Long,
        uuid: String,
    ) {
        taskRepository.deleteTaskById(id = id)
        cancelNotificationWorkUseCase(id = uuid)
    }
}
