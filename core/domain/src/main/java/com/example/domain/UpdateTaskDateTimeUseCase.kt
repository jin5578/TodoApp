package com.example.domain

import com.example.data_api.repository.TaskRepository
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class UpdateTaskDateTimeUseCase
@Inject
constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(
        id: Long,
        date: LocalDate,
        time: LocalDateTime?,
        reminderTime: LocalDateTime?,
    ) = taskRepository.updateTaskDateTime(
        id = id,
        date = date,
        time = time,
        reminderTime = reminderTime,
    )
}
