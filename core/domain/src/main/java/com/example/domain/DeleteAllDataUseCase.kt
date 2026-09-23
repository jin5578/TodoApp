package com.example.domain

import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteAllDataUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
    private val categoryRepository: CategoryRepository,
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke() = coroutineScope {
        launch { systemRepository.deleteAllData() }
        launch { categoryRepository.deleteAllCategory() }
        launch { taskRepository.deleteAllTask() }
    }
}
