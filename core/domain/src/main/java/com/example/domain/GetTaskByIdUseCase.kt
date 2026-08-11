package com.example.domain

import com.example.data_api.repository.TaskRepository
import com.example.model.Task
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(id: Long): Task =
        taskRepository.getTaskById(id)
}