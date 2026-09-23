package com.example.domain

import com.example.data_api.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskTitleUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(id: Long, title: String) {
        taskRepository.updateTaskTitle(id = id, title = title)
    }
}