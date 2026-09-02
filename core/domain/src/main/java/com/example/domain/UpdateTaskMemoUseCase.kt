package com.example.domain

import com.example.data_api.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskMemoUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(
        taskId: Long,
        memoTitle: String,
        memoContent: String
    ): Result<Unit> = runCatching {
        taskRepository.updateTaskMemo(
            taskId = taskId,
            memoTitle = memoTitle,
            memoContent = memoContent
        )
    }
}