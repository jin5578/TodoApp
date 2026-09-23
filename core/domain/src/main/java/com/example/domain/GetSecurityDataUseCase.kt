package com.example.domain

import com.example.data_api.repository.SystemRepository
import com.example.model.security.SecuritySystem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSecurityDataUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
) {
    operator fun invoke(): Flow<SecuritySystem> = systemRepository.getSecuritySystem()
}
