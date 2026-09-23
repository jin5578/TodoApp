package com.example.data_api.repository

import com.example.model.location.Coordinates

interface LocationRepository {
    suspend fun getCurrentCoordinates(): Coordinates?
}