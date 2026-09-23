package com.example.domain

import com.example.data_api.repository.SystemRepository
import com.example.model.ThemeType
import javax.inject.Inject

class UpdateThemeTypeUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
) {
    suspend operator fun invoke(themeType: ThemeType) = systemRepository.updateThemeType(themeType = themeType)
}
