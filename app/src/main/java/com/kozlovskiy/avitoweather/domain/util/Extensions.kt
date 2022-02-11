package com.kozlovskiy.avitoweather.domain.util

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.kozlovskiy.avitoweather.domain.model.SimpleLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.awaitLastLocation(): SimpleLocation? =
    suspendCancellableCoroutine { continuation ->
        lastLocation.addOnSuccessListener { location ->
            continuation.resume(
                SimpleLocation(
                    latitude = location.latitude,
                    longitude = location.longitude
                ),
                onCancellation = null
            )
        }.addOnFailureListener { e ->
            continuation.resumeWithException(e)
        }
    }