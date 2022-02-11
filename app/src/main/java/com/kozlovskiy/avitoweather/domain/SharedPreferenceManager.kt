package com.kozlovskiy.avitoweather.domain

import android.content.SharedPreferences
import androidx.core.content.edit
import com.kozlovskiy.avitoweather.domain.model.SimpleLocation
import javax.inject.Inject
import javax.inject.Singleton

interface SharedPreferenceManager {
    fun getStoredLocation(): SimpleLocation?
    fun storeLocation(latitude: Float, longitude: Float)
    fun chooseMyLocation()
}

@Singleton
class SharedPreferenceManagerImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : SharedPreferenceManager {

    override fun getStoredLocation(): SimpleLocation? {
        val latitude = sharedPreferences.getFloat(KEY_STORED_LATITUDE, VALUE_MY_LOCATION)
        val longitude = sharedPreferences.getFloat(KEY_STORED_LONGITUDE, VALUE_MY_LOCATION)

        return if (latitude != -1f && longitude != -1f) {
            SimpleLocation(latitude, longitude)
        } else {
            null
        }
    }

    override fun storeLocation(latitude: Float, longitude: Float) {
        sharedPreferences.edit {
            putFloat(KEY_STORED_LATITUDE, latitude)
            putFloat(KEY_STORED_LONGITUDE, longitude)
        }
    }

    override fun chooseMyLocation() {
        sharedPreferences.edit {
            putFloat(KEY_STORED_LATITUDE, VALUE_MY_LOCATION)
            putFloat(KEY_STORED_LONGITUDE, VALUE_MY_LOCATION)
        }
    }

    companion object {
        const val KEY_STORED_LATITUDE = "stored.latitude"
        const val KEY_STORED_LONGITUDE = "stored.longitude"

        const val VALUE_MY_LOCATION = -1f
    }
}