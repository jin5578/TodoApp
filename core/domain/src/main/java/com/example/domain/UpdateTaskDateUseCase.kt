package com.example.domain

import com.example.data_api.repository.TaskRepository
import java.time.LocalDate
import javax.inject.Inject

class UpdateTaskDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(id: Long, date: LocalDate) =
        taskRepository.updateTaskDate(id = id, date = date)
}