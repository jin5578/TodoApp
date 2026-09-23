package com.example.domain

import com.example.data_api.repository.SystemRepository
import com.example.model.ThemeType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeTypeUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
) {
    operator fun invoke(): Flow<ThemeType> = systemRepository.getThemeType()
}
