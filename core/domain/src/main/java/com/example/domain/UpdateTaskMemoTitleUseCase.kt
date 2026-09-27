package com.example.domain

import com.example.dataApi.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskMemoTitleUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(
        id: Long,
        memoTitle: String,
    ) = taskRepository.updateTaskMemoTitle(
        id = id,
        memoTitle = memoTitle,
    )
}
