package com.example.domain

import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.home.Home
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
    private val categoryRepository: CategoryRepository,
    private val taskRepository: TaskRepository
) {
    operator fun invoke(categoryId: Long): Flow<Home> =
        combine(
            flow =
                if (categoryId == -1L) taskRepository.getTasks()
                else taskRepository.getTasksByCategory(categoryId = categoryId),
            flow2 = categoryRepository.getAllCategory(),
            flow3 = systemRepository.getHomeSystem()
        ) { tasks, categories, homeSystem ->
            Home(
                tasks = tasks,
                categories = categories,
                homeSystem = homeSystem
            )
        }
}