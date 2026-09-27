package com.example.domain

import com.example.dataApi.repository.SystemRepository
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
