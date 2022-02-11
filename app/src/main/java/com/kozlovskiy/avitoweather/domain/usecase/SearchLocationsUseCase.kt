package com.kozlovskiy.avitoweather.domain.usecase

import com.kozlovskiy.avitoweather.common.Result
import com.kozlovskiy.avitoweather.di.qualifier.IoDispatcher
import com.kozlovskiy.avitoweather.domain.model.SimpleLocation
import com.kozlovskiy.avitoweather.domain.repository.GeocoderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchLocationsUseCase @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val geocoderRepository: GeocoderRepository,
) {

    operator fun invoke(query: String): Flow<LocationsResult> = flow {
        emit(LocationsResult.Loading)

        val locations = geocoderRepository.getLocationsByQuery(query, LOCATIONS_LIMIT)

        when (locations) {
            is Result.Error -> {
                emit(LocationsResult.Failure(locations.exception))
            }
            is Result.Success -> {
                emit(LocationsResult.Success(locations.data))
            }
        }
    }.flowOn(dispatcher)

    companion object {
        const val LOCATIONS_LIMIT = 5
    }
}

sealed class LocationsResult {
    object Loading : LocationsResult()

    data class Success(val locations: List<SimpleLocation>) : LocationsResult()
    data class Failure(val ex: Exception) : LocationsResult()
}