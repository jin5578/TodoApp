package com.example.domain

import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.calendar.Calendar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCalendarDataUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<Calendar> =
        combine(
            flow = systemRepository.getCalendarSystem(),
            flow2 = taskRepository.getAllTask(),
            flow3 = categoryRepository.getAllCategory()
        ) { calendarSystem, tasks, categories ->
            Calendar(
                tasks = tasks,
                categories = categories,
                calendarSystem = calendarSystem
            )
        }
}