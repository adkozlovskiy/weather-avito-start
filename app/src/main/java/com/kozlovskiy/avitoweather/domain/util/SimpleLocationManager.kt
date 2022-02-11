package com.kozlovskiy.avitoweather.domain.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.kozlovskiy.avitoweather.domain.model.SimpleLocation
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SimpleLocationManager @Inject constructor(
    private val locationProviderClient: FusedLocationProviderClient,
    @ApplicationContext private val appContext: Context
) {

    // TODO check for google services installed or use default location manager
    suspend fun askForLastLocation(): SimpleLocationResult {
        // Checking for permissions
        if (ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return SimpleLocationResult.NoPermission
        } else try {
            val simpleLocation = locationProviderClient.awaitLastLocation()

            return if (simpleLocation == null) {
                SimpleLocationResult.NullLocation
            } else {
                SimpleLocationResult.Success(simpleLocation)
            }
        } catch (ex: Exception) {
            return SimpleLocationResult.Failure(ex)
        }
    }
}

sealed class SimpleLocationResult {
    object NoPermission : SimpleLocationResult()
    object NullLocation : SimpleLocationResult()

    class Success(val location: SimpleLocation) : SimpleLocationResult()
    class Failure(val exception: Exception) : SimpleLocationResult()
}