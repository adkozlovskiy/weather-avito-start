package com.kozlovskiy.avitoweather.data.repository

import com.kozlovskiy.avitoweather.common.Result
import com.kozlovskiy.avitoweather.data.api.OpenweatherService
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation
import com.kozlovskiy.avitoweather.domain.repository.GeocoderRepository
import com.kozlovskiy.avitoweather.domain.util.LocaleUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeocoderRepositoryImpl @Inject constructor(
    private val openweatherService: OpenweatherService,
    private val localeUtils: LocaleUtils,
) : GeocoderRepository {

    override suspend fun getLocationsByQuery(
        query: String,
        limit: Int
    ): Result<List<SimpleLocation>> {
        return Result.catch {
            openweatherService.getLocationsByQuery(query, limit)
                .map {
                    it.toSimpleLocation(localeUtils)
                }
        }
    }
}