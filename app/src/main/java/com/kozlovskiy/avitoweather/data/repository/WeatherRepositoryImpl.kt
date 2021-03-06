package com.kozlovskiy.avitoweather.data.repository

import com.kozlovskiy.avitoweather.common.Result
import com.kozlovskiy.avitoweather.data.api.OpenweatherService
import com.kozlovskiy.avitoweather.di.qualifier.DefaultAdditionalMapper
import com.kozlovskiy.avitoweather.di.qualifier.DefaultResolver
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation
import com.kozlovskiy.avitoweather.domain.model.summary.Current
import com.kozlovskiy.avitoweather.domain.model.summary.OneCall
import com.kozlovskiy.avitoweather.domain.repository.WeatherRepository
import com.kozlovskiy.avitoweather.domain.util.AdditionalCurrentMapper
import com.kozlovskiy.avitoweather.domain.util.IconResolver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val openweatherService: OpenweatherService,
    @DefaultResolver
    private val iconResolver: IconResolver,
    @DefaultAdditionalMapper
    private val additionalCurrentMapper: AdditionalCurrentMapper,
) : WeatherRepository {
    override suspend fun getCurrentWeather(location: SimpleLocation): Result<Current> {
        return Result.catch {
            openweatherService
                .getCurrentWeather(location.latitude, location.longitude)
                .toCurrent(iconResolver)
        }
    }

    override suspend fun getOneCallWeather(location: SimpleLocation): Result<OneCall> {
        return Result.catch {
            openweatherService
                .getOneCallWeather(location.latitude, location.longitude)
                .toOneCall(iconResolver, additionalCurrentMapper)
        }
    }
}