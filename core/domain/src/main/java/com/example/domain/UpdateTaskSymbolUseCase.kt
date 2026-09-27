package com.example.domain

import com.example.dataApi.repository.TaskRepository
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
