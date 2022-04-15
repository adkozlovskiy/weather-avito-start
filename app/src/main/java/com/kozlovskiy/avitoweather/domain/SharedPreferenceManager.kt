package com.kozlovskiy.avitoweather.domain

import android.content.SharedPreferences
import androidx.core.content.edit
import com.kozlovskiy.avitoweather.domain.model.Unit
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation
import javax.inject.Inject
import javax.inject.Singleton

interface SharedPreferenceManager {
    /**
     * Получить сохраненную локацию.
     */
    fun getStoredLocation(): SimpleLocation?

    /**
     * Сохранить локацию.
     */
    fun storeLocation(latitude: Float, longitude: Float)

    /**
     * Сохранить локацию как текущую.
     */
    fun chooseMyLocation()

    /**
     * Получить сохраненный юнит.
     */
    fun getStoredUnit(): Unit

    /**
     * Сохранить юнит.
     */
    fun storeUnit(unit: Unit)
}

@Singleton
class SharedPreferenceManagerImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : SharedPreferenceManager {

    override fun getStoredLocation(): SimpleLocation? {
        val latitude = sharedPreferences.getFloat(KEY_STORED_LATITUDE, VALUE_MY_LOCATION)
        val longitude = sharedPreferences.getFloat(KEY_STORED_LONGITUDE, VALUE_MY_LOCATION)

        return if (latitude != -1F && longitude != -1F) {
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

    override fun getStoredUnit(): Unit {
        val unitOrdinal = sharedPreferences.getInt(KEY_UNIT, 0)
        return Unit.values()[unitOrdinal]
    }

    override fun storeUnit(unit: Unit) {
        val unitOrdinal = unit.ordinal
        sharedPreferences.edit {
            putInt(KEY_UNIT, unitOrdinal)
        }
    }

    companion object {
        const val KEY_STORED_LATITUDE = "stored.latitude"
        const val KEY_STORED_LONGITUDE = "stored.longitude"

        const val VALUE_MY_LOCATION = -1F

        const val KEY_UNIT = "stored.unit"
    }
}