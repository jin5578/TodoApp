package com.example.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.dataApi.repository.LocationRepository
import com.example.model.location.Coordinates
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

internal class DefaultLocationRepository(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : LocationRepository {
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentCoordinates(): Coordinates? {
        if (!hasLocationPermission()) return null

        val cancellationTokenSource = CancellationTokenSource()
        return suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient
                .getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    cancellationTokenSource.token,
                ).addOnSuccessListener { location ->
                    val coordinates =
                        location?.let {
                            Coordinates(
                                latitude = it.latitude,
                                longitude = it.longitude,
                            )
                        }
                    continuation.resume(coordinates)
                }.addOnFailureListener {
                    continuation.resume(null)
                }

            continuation.invokeOnCancellation {
                cancellationTokenSource.cancel()
            }
        }
    }

    private fun hasLocationPermission(): Boolean = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    ) == PackageManager.PERMISSION_GRANTED
}
