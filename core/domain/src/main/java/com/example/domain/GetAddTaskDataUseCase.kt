package com.example.domain

import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.addtask.AddTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class GetAddTaskDataUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(date: LocalDate): Flow<AddTask> =
        combine(
            flow = systemRepository.getAddTaskSystem(),
            flow2 = taskRepository.getTaskCountByDate(date = date),
            flow3 = categoryRepository.getAllCategory(),
        ) { addTaskSystem, taskCount, categories ->
            AddTask(
                date = date,
                taskCount = taskCount,
                categories = categories,
                addTaskSystem = addTaskSystem
            )
        }
}