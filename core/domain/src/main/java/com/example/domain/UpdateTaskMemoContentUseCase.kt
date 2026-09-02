package com.example.domain

import com.example.data_api.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskMemoContentUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(
        id: Long,
        memoContent: String,
    ) = taskRepository.updateTaskMemoContent(
        id = id,
        memoContent = memoContent,
    )
}