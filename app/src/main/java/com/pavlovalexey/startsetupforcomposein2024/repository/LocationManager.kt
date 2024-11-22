package com.pavlovalexey.startsetupforcomposein2024.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(): Flow<Location?> = callbackFlow {
        val locationCallback = object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    trySend(locationResult.lastLocation)
                }
            }
        }

        val request = com.google.android.gms.location.LocationRequest.create().apply {
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
            numUpdates = 1
        }

        fusedLocationClient.requestLocationUpdates(request, locationCallback, null)

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
}