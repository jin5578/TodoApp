package com.example.domain

import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.profile.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class GetProfileDataUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val systemRepository: SystemRepository
) {
    operator fun invoke(fromDate: LocalDate, toDate: LocalDate): Flow<Profile> =
        combine(
            flow = taskRepository.getTasksByDateRange(
                fromDate = fromDate,
                toDate = toDate,
            ),
            flow2 = systemRepository.getProfileSystem()
        ) { tasks, profileSystem ->
            Profile(
                tasks = tasks,
                profileSystem = profileSystem
            )
        }
}