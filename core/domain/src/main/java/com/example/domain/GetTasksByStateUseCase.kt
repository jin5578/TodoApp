package com.example.domain

import com.example.dataApi.repository.CategoryRepository
import com.example.dataApi.repository.SystemRepository
import com.example.dataApi.repository.TaskRepository
import com.example.model.tasks.Tasks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTasksByStateUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
    private val systemRepository: SystemRepository,
) {
    operator fun invoke(isCompleted: Boolean): Flow<Tasks> = combine(
        flow = taskRepository.getTasksByState(isCompleted = isCompleted),
        flow2 = categoryRepository.getAllCategory(),
        flow3 = systemRepository.getTasksSystem(),
    ) { tasks, categories, tasksSystem ->
        Tasks(
            tasks = tasks,
            categories = categories,
            tasksSystem = tasksSystem,
        )
    }
}
