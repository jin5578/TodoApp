package com.example.domain

import com.example.dataApi.repository.LocationRepository
import com.example.model.location.Coordinates
import javax.inject.Inject

class GetCurrentLocationUseCase
@Inject
constructor(
    private val locationRepository: LocationRepository,
) {
    suspend operator fun invoke(): Coordinates? = locationRepository.getCurrentCoordinates()
}
