package com.example.domain

import com.example.data_api.repository.TaskRepository
import javax.inject.Inject

class DeleteTasksByStateUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(isCompleted: Boolean) =
        taskRepository.deleteTasksByState(isCompleted = isCompleted)
}