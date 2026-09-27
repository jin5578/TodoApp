package com.example.domain

import com.example.dataApi.repository.CategoryRepository
import com.example.dataApi.repository.TaskRepository
import com.example.model.manageCategories.ManageCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetManageCategoriesDataUseCase
@Inject
constructor(
    private val categoryRepository: CategoryRepository,
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(): Flow<ManageCategories> = combine(
        flow = categoryRepository.getAllCategory(),
        flow2 = taskRepository.getTasks(),
    ) { categories, tasks ->
        ManageCategories(
            categories = categories,
            tasks = tasks,
        )
    }
}
