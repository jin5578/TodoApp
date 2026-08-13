package com.example.domain

import com.example.data_api.repository.SystemRepository
import com.example.model.SortTaskType
import javax.inject.Inject

class UpdateSortTaskTypeUseCase @Inject constructor(
    private val systemRepository: SystemRepository
) {
    suspend operator fun invoke(sortTaskType: SortTaskType) =
        systemRepository.updateSortTaskType(sortTaskType = sortTaskType)
}