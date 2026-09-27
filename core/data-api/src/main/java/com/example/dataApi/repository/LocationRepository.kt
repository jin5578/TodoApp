package com.example.dataApi.repository

import com.example.model.location.Coordinates

interface LocationRepository {
    suspend fun getCurrentCoordinates(): Coordinates?
}
