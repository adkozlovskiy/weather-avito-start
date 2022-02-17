package com.kozlovskiy.avitoweather.domain.util

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.HandlerThread
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

fun Location?.asSimpleLocation(): SimpleLocation? {
    return if (this == null) null
    else SimpleLocation(
        latitude = this.latitude.toFloat(),
        longitude = this.longitude.toFloat()
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.awaitLastLocation(): SimpleLocation? =
    suspendCancellableCoroutine { continuation ->
        lastLocation.addOnSuccessListener { location ->
            continuation.resume(
                location.asSimpleLocation(),
                onCancellation = null
            )
        }.addOnFailureListener { e ->
            continuation.resumeWithException(e)
        }
    }

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.awaitLastLocationUpdate(
    locationRequest: LocationRequest,
): SimpleLocation? =
    suspendCancellableCoroutine { continuation ->
        val hd = HandlerThread("")
        hd.start()
        val callback = object : LocationCallback() {
            override fun onLocationResult(r: LocationResult) {
                try {
                    continuation.resume(
                        r.lastLocation.asSimpleLocation(),
                        onCancellation = null
                    )
                } catch (ex: Exception) {
                    continuation.resumeWithException(ex)
                }
                removeLocationUpdates(this)
            }
        }
        requestLocationUpdates(locationRequest, callback, hd.looper)
        continuation.invokeOnCancellation { removeLocationUpdates(callback) }
    }

fun LocationManager.getLastLocation(provider: String): SimpleLocation? {
    return try {
        getLastKnownLocation(provider).asSimpleLocation()
    } catch (ex: SecurityException) {
        null
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("MissingPermission")
suspend fun LocationManager.awaitLastLocationUpdate(
    provider: String,
): SimpleLocation? = suspendCancellableCoroutine { continuation ->
    Log.d("TAG", "awaitLastLocationUpdate: ")
    val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            continuation.resume(location.asSimpleLocation(), onCancellation = null)
            removeUpdates(this)
        }
    }
    requestLocationUpdates(provider, 1L, 1f, locationListener)
    continuation.invokeOnCancellation { removeUpdates(locationListener) }

}