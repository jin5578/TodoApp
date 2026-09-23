package com.example.domain

import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.completed_tasks.CompletedTasks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCompletedTasksDataUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(): Flow<CompletedTasks> = combine(
        flow = taskRepository.getTasksByState(isCompleted = true),
        flow2 = systemRepository.getCompletedTasksSystem(),
    ) { tasks, completedTasksSystem ->
        CompletedTasks(
            tasks = tasks,
            completedTasksSystem = completedTasksSystem,
        )
    }
}
