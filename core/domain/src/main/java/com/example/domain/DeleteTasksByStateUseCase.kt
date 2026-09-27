package com.example.domain

import com.example.dataApi.repository.TaskRepository
import javax.inject.Inject

class DeleteTasksByStateUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(isCompleted: Boolean) = taskRepository.deleteTasksByState(isCompleted = isCompleted)
}
