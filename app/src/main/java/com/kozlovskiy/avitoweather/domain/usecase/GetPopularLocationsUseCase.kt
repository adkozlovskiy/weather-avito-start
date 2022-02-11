package com.kozlovskiy.avitoweather.domain.usecase

import com.kozlovskiy.avitoweather.domain.model.LocationListItem
import com.kozlovskiy.avitoweather.domain.repository.LocationsRepository
import javax.inject.Inject

class GetPopularLocationsUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository,
) {

    suspend operator fun invoke(): List<LocationListItem> {
        return locationsRepository.getPopularLocations()
    }
}