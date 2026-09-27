package com.example.domain

import com.example.dataApi.repository.SystemRepository
import com.example.dataApi.repository.TaskRepository
import com.example.model.completedTasks.CompletedTasks
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
