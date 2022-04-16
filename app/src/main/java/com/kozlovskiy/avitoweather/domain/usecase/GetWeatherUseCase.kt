package com.kozlovskiy.avitoweather.domain.usecase

import com.kozlovskiy.avitoweather.common.Result
import com.kozlovskiy.avitoweather.di.qualifier.IoDispatcher
import com.kozlovskiy.avitoweather.domain.SharedPreferenceManager
import com.kozlovskiy.avitoweather.domain.SimpleLocationManager
import com.kozlovskiy.avitoweather.domain.SimpleLocationResult
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation
import com.kozlovskiy.avitoweather.domain.model.summary.OneCall
import com.kozlovskiy.avitoweather.domain.repository.WeatherRepository
import com.kozlovskiy.avitoweather.domain.util.GeocoderUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository,
    private val locationManager: SimpleLocationManager,
    private val geocoderUtils: GeocoderUtils,
    private val sharedPreferenceManager: SharedPreferenceManager,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(): WeatherResult = withContext(dispatcher) {
        // Check if we have manually selected location.
        val storedLocation = sharedPreferenceManager.getStoredLocation()
        val simpleLocationResult = storedLocation
            ?.let { SimpleLocationResult.Success(it) }
            ?: locationManager.askForLocation()

        return@withContext when (simpleLocationResult) {
            is SimpleLocationResult.Failure -> {
                WeatherResult.Failure(simpleLocationResult.exception)
            }
            is SimpleLocationResult.NoPermission -> {
                WeatherResult.NoPermission
            }
            is SimpleLocationResult.NullLocation -> {
                WeatherResult.NullLocation
            }
            is SimpleLocationResult.Success -> {
                val location = simpleLocationResult.location
                    .let {
                        geocoderUtils.completeLocationWithName(it)
                    }
                getWeatherResult(location)
            }
        }
    }

    private suspend fun getWeatherResult(location: SimpleLocation): WeatherResult {
        return when (
            val result = repository.getOneCallWeather(location)
        ) {
            is Result.Error -> WeatherResult.Failure(result.exception)
            is Result.Success -> WeatherResult.Success(location, result.data)
        }
    }
}

sealed class WeatherResult {
    object NoPermission : WeatherResult()
    object NullLocation : WeatherResult()

    class Success(val location: SimpleLocation, val oneCall: OneCall) : WeatherResult()
    class Failure(val exception: Exception) : WeatherResult()
}