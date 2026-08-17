package com.example.domain

import com.example.data_api.repository.SystemRepository
import com.example.model.lock_setup.LockSetupSystem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLockSetupDataUseCase @Inject constructor(
    private val systemRepository: SystemRepository
) {
    operator fun invoke(): Flow<LockSetupSystem> =
        systemRepository.getLockSetupSystem()
}