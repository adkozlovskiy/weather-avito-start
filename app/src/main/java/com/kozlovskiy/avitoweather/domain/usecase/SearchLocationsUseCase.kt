package com.kozlovskiy.avitoweather.domain.usecase

import com.kozlovskiy.avitoweather.common.Result
import com.kozlovskiy.avitoweather.di.qualifier.IoDispatcher
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation
import com.kozlovskiy.avitoweather.domain.repository.GeocoderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchLocationsUseCase @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val geocoderRepository: GeocoderRepository,
) {

    suspend operator fun invoke(query: String): LocationsResult = withContext(dispatcher) {
        return@withContext when (
            val locations = geocoderRepository.getLocationsByQuery(query, LOCATIONS_LIMIT)
        ) {
            is Result.Error -> {
                LocationsResult.Failure(locations.exception)
            }
            is Result.Success -> {
                LocationsResult.Success(locations.data)
            }
        }
    }

    companion object {
        const val LOCATIONS_LIMIT = 5
    }
}

sealed class LocationsResult {
    data class Success(val locations: List<SimpleLocation>) : LocationsResult()
    data class Failure(val ex: Exception) : LocationsResult()
}