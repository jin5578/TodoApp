package com.example.domain

import com.example.data_api.repository.TaskRepository
import com.example.model.Task
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.updateTask(task)
    }
}