package com.example.domain

import com.example.data_api.repository.SystemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHasBiometricEnabledUseCase @Inject constructor(
    private val systemRepository: SystemRepository
) {
    operator fun invoke(): Flow<Boolean> =
        systemRepository.hasBiometricEnabled()
}