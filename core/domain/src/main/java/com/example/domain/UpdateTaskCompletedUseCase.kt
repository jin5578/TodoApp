package com.example.domain

import com.example.data_api.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskCompletedUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(
        id: Long,
        isCompleted: Boolean,
    ) = taskRepository.updateTaskCompleted(id = id, isCompleted = isCompleted)
}
