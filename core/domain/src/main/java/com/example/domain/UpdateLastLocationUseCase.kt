package com.example.domain

import com.example.dataApi.repository.SystemRepository
import javax.inject.Inject

class UpdateLastLocationUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
    ) = systemRepository.updateLastLocation(
        latitude = latitude,
        longitude = longitude,
    )
}
