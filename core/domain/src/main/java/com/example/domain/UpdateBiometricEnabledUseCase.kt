package com.example.domain

import com.example.dataApi.repository.SystemRepository
import javax.inject.Inject

class UpdateBiometricEnabledUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
) {
    suspend operator fun invoke(enabled: Boolean) = systemRepository.updateBiometricEnabled(enabled = enabled)
}
