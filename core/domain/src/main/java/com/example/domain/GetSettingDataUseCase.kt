package com.example.domain

import com.example.dataApi.repository.SystemRepository
import com.example.model.setting.SettingSystem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingDataUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
) {
    operator fun invoke(): Flow<SettingSystem> = systemRepository.getSettingSystem()
}
