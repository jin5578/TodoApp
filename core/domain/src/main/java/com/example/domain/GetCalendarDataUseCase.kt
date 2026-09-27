package com.example.domain

import com.example.dataApi.repository.CategoryRepository
import com.example.dataApi.repository.SystemRepository
import com.example.dataApi.repository.TaskRepository
import com.example.model.calendar.Calendar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCalendarDataUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
    private val systemRepository: SystemRepository,
) {
    operator fun invoke(categoryId: Long): Flow<Calendar> = combine(
        flow =
        if (categoryId == -1L) {
            taskRepository.getTasks()
        } else {
            taskRepository.getTasksByCategory(categoryId = categoryId)
        },
        flow2 = categoryRepository.getAllCategory(),
        flow3 = systemRepository.getCalendarSystem(),
    ) { tasks, categories, calendarSystem ->
        Calendar(
            tasks = tasks,
            categories = categories,
            calendarSystem = calendarSystem,
        )
    }
}
