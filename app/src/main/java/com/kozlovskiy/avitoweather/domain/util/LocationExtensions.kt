package com.kozlovskiy.avitoweather.domain.util

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.suspendCoroutine

fun Location?.asSimpleLocation(): SimpleLocation? {
    return if (this == null) null
    else SimpleLocation(
        latitude = latitude.toFloat(),
        longitude = longitude.toFloat()
    )
}

@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.awaitLastLocation(): SimpleLocation? =
    suspendCoroutine { continuation ->
        lastLocation.addOnSuccessListener { location ->
            continuation.resumeWith(
                Result.success(location.asSimpleLocation()),
            )
        }.addOnFailureListener { ex: Exception ->
            continuation.resumeWith(
                Result.failure(ex)
            )
        }
    }

@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.awaitLastLocationUpdate(
    locationRequest: LocationRequest,
): SimpleLocation? = suspendCancellableCoroutine { continuation ->
    val callback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            try {
                continuation.resumeWith(
                    Result.success(
                        result.lastLocation.asSimpleLocation()
                    )
                )
            } catch (ex: Exception) {
                continuation.resumeWith(
                    Result.failure(ex)
                )
            } finally {
                removeLocationUpdates(this)
            }
        }
    }
    requestLocationUpdates(locationRequest, callback, Looper.getMainLooper())
    continuation.invokeOnCancellation { removeLocationUpdates(callback) }
}

fun LocationManager.getLastLocation(provider: String): SimpleLocation? {
    return try {
        getLastKnownLocation(provider).asSimpleLocation()
    } catch (ex: SecurityException) {
        null
    }
}

@SuppressLint("MissingPermission")
suspend fun LocationManager.awaitLastLocationUpdate(
    provider: String,
): SimpleLocation? = withContext(Dispatchers.Main) {
    suspendCancellableCoroutine { continuation ->
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                try {
                    continuation.resumeWith(
                        Result.success(location.asSimpleLocation()),
                    )
                } catch (ex: Exception) {
                    continuation.resumeWith(
                        Result.failure(ex)
                    )
                } finally {
                    // Removing location updates.
                    removeUpdates(this)
                }
            }
        }
        requestLocationUpdates(provider, 0L, 0F, locationListener)
        continuation.invokeOnCancellation { removeUpdates(locationListener) }
    }
}