package com.kozlovskiy.avitoweather.domain.usecase

import com.kozlovskiy.avitoweather.common.Result
import com.kozlovskiy.avitoweather.di.qualifier.IoDispatcher
import com.kozlovskiy.avitoweather.domain.repository.WeatherRepository
import com.kozlovskiy.avitoweather.domain.model.OneCall
import com.kozlovskiy.avitoweather.domain.model.SimpleLocation
import com.kozlovskiy.avitoweather.domain.util.GeocoderUtils
import com.kozlovskiy.avitoweather.domain.util.SimpleLocationManager
import com.kozlovskiy.avitoweather.domain.util.SimpleLocationResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository,
    private val locationManager: SimpleLocationManager,
    private val geocoderUtils: GeocoderUtils,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(): Flow<WeatherResult> = flow {
        emit(WeatherResult.Loading)

        val simpleLocationResult: SimpleLocationResult =
            SimpleLocationResult.Success(
                SimpleLocation(
                    latitude = 55.7146576,
                    longitude = 37.8022313
                )
            )
//        val simpleLocationResult = locationManager.askForLastLocation()
        when (simpleLocationResult) {
            is SimpleLocationResult.Failure -> {
                emit(WeatherResult.Failure(simpleLocationResult.exception))
            }
            is SimpleLocationResult.NoPermission -> {
                emit(WeatherResult.NoPermission)
            }
            is SimpleLocationResult.NullLocation -> {
                emit(WeatherResult.BadLocation)
            }
            is SimpleLocationResult.Success -> {
                val location = simpleLocationResult.location
                    .let {
                        geocoderUtils.completeLocationWithName(it)
                    }
                val oneCallResult = getWeatherResult(location)
                emit(oneCallResult)
            }
        }
    }.flowOn(dispatcher)

    private suspend fun getWeatherResult(location: SimpleLocation): WeatherResult {
        val result = repository.getOneCallWeather(location)
        return when (result) {
            is Result.Error -> WeatherResult.Failure(result.exception)
            is Result.Success -> WeatherResult.Success(location, result.data)
        }
    }
}

sealed class WeatherResult {
    object Loading : WeatherResult()
    object NoPermission : WeatherResult()
    object BadLocation : WeatherResult()

    class Success(val location: SimpleLocation, val oneCall: OneCall) : WeatherResult()
    class Failure(val exception: Exception) : WeatherResult()
}