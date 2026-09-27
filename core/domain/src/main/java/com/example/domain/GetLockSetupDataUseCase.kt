package com.example.domain

import com.example.dataApi.repository.SystemRepository
import com.example.model.lockSetup.LockSetupSystem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLockSetupDataUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
) {
    operator fun invoke(): Flow<LockSetupSystem> = systemRepository.getLockSetupSystem()
}
