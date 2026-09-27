package com.example.domain

import com.example.dataApi.repository.SubTaskRepository
import javax.inject.Inject

class UpdateSubTaskCompletedUseCase
@Inject
constructor(
    private val subTaskRepository: SubTaskRepository,
) {
    suspend operator fun invoke(
        id: Long,
        isCompleted: Boolean,
    ) = subTaskRepository.updateSubTaskCompleted(
        id = id,
        isCompleted = isCompleted,
    )
}
