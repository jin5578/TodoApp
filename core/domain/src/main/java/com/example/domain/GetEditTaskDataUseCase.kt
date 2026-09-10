package com.example.domain

import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.edittask.EditTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetEditTaskDataUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke(id: Long): Flow<EditTask> =
        combine(
            flow = systemRepository.getEditTaskSystem(),
            flow2 = taskRepository.getTaskById(id = id),
            flow3 = categoryRepository.getAllCategory()
        ) { editTaskSystem, task, categories ->
            EditTask(
                task = task,
                categories = categories,
                editTaskSystem = editTaskSystem
            )
        }
}