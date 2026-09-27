package com.example.domain

import com.example.dataApi.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskCategoryUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(
        taskId: Long,
        categoryId: Long,
    ) = taskRepository.updateTaskCategory(
        taskId = taskId,
        categoryId = categoryId,
    )
}
