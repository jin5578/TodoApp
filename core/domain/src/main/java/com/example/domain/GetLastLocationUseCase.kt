package com.example.domain

import android.location.Location
import com.example.data_api.repository.SystemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLastLocationUseCase @Inject constructor(
    private val systemRepository: SystemRepository
) {
    operator fun invoke(): Flow<Location> =
        systemRepository.getLastLocation()
}