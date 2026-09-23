package com.example.domain

import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.profile.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class GetProfileDataUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
    private val systemRepository: SystemRepository
) {
    operator fun invoke(): Flow<Profile> =
        combine(
            flow = taskRepository.getTasks(),
            flow2 = categoryRepository.getAllCategory(),
            flow3 = systemRepository.getProfileSystem()
        ) { tasks, categories, profileSystem ->
            Profile(
                tasks = tasks,
                categories = categories,
                profileSystem = profileSystem
            )
        }
}