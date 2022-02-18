package com.kozlovskiy.avitoweather.domain

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation
import com.kozlovskiy.avitoweather.domain.util.awaitLastLocation
import com.kozlovskiy.avitoweather.domain.util.awaitLastLocationUpdate
import com.kozlovskiy.avitoweather.domain.util.getLastLocation
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SimpleLocationManager @Inject constructor(
    private val locationProvider: FusedLocationProviderClient,
    private val locationManager: LocationManager,
    @ApplicationContext
    private val appContext: Context,
) {

    suspend fun askForLocation(): SimpleLocationResult {
        return if (!isLocationPermissionGranted()) {
            SimpleLocationResult.NoPermission
        } else if (isGooglePlayServicesAvailable()) {
            askForFusedLocation()
        } else {
            askForLegacyLocation()
        }
    }

    private suspend fun askForFusedLocation(): SimpleLocationResult {
        return try {
            val simpleLocation = locationProvider.awaitLastLocation()

            if (simpleLocation == null) {
                val lastLocationUpdate =
                    locationProvider.awaitLastLocationUpdate(LocationRequest.create())

                lastLocationUpdate?.let {
                    SimpleLocationResult.Success(it)
                } ?: SimpleLocationResult.NullLocation

            } else {
                SimpleLocationResult.Success(simpleLocation)
            }
        } catch (ex: SecurityException) {
            SimpleLocationResult.NoPermission
        } catch (ex: Exception) {
            SimpleLocationResult.Failure(ex)
        }
    }

    private suspend fun askForLegacyLocation(): SimpleLocationResult {
        return try {
            val simpleLocation = locationManager
                .getLastLocation(LocationManager.GPS_PROVIDER)

            if (simpleLocation == null) {
                val lastLocationUpdate =
                    locationManager.awaitLastLocationUpdate(LocationManager.GPS_PROVIDER)
                lastLocationUpdate?.let {
                    SimpleLocationResult.Success(it)
                } ?: SimpleLocationResult.NullLocation
            } else {
                SimpleLocationResult.Success(simpleLocation)
            }
        } catch (ex: Exception) {
            SimpleLocationResult.Failure(ex)
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        return GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(appContext) == ConnectionResult.SUCCESS
    }
}

sealed class SimpleLocationResult {
    object NoPermission : SimpleLocationResult()
    object NullLocation : SimpleLocationResult()

    class Success(val location: SimpleLocation) : SimpleLocationResult()
    class Failure(val exception: Exception) : SimpleLocationResult()
}