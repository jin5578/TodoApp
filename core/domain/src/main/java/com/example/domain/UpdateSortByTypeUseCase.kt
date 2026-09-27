package com.example.domain

import com.example.dataApi.repository.SystemRepository
import com.example.model.SortByType
import javax.inject.Inject

class UpdateSortByTypeUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
) {
    suspend operator fun invoke(sortByType: SortByType) = systemRepository.updateSortByType(sortByType = sortByType)
}
