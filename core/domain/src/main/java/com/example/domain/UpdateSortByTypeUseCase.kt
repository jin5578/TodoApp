package com.example.domain

import com.example.data_api.repository.SystemRepository
import com.example.model.SortByType
import javax.inject.Inject

class UpdateSortByTypeUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
) {
    suspend operator fun invoke(sortByType: SortByType) = systemRepository.updateSortByType(sortByType = sortByType)
}
