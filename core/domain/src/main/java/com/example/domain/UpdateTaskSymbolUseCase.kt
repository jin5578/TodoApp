package com.example.domain

import com.example.data_api.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskSymbolUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(
        taskId: Long,
        symbolId: Int,
    ) = taskRepository.updateTaskSymbol(taskId = taskId, symbolId = symbolId)
}
