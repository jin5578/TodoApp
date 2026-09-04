package com.example.domain

import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.SubTaskRepository
import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.edittask.EditTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetEditTaskDataUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
    private val taskRepository: TaskRepository,
    private val subTaskRepository: SubTaskRepository,
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke(id: Long): Flow<EditTask> =
        combine(
            flow = systemRepository.getEditTaskSystem(),
            flow2 = taskRepository.getFlowTaskById(id = id),
            flow3 = subTaskRepository.getSubTasksByParentId(parentId = id),
            flow4 = categoryRepository.getAllCategory()
        ) { editTaskSystem, task, subTasks, categories ->
            EditTask(
                task = task,
                subTasks = subTasks,
                categories = categories,
                editTaskSystem = editTaskSystem
            )
        }
}