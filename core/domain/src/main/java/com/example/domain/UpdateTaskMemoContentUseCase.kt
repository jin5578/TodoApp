package com.example.domain

import com.example.dataApi.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskMemoContentUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(
        id: Long,
        memoContent: String,
    ) = taskRepository.updateTaskMemoContent(
        id = id,
        memoContent = memoContent,
    )
}
