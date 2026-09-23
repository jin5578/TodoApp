package com.example.domain

import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.tasks.Tasks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTasksDataUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
    private val categoryRepository: CategoryRepository,
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(categoryId: Long): Flow<Tasks> = combine(
        flow =
        if (categoryId == -1L) {
            taskRepository.getTasks()
        } else {
            taskRepository.getTasksByCategory(categoryId = categoryId)
        },
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
