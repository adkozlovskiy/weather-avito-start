package com.kozlovskiy.avitoweather.domain.repository

import com.kozlovskiy.avitoweather.domain.model.LocationListItem
import javax.inject.Inject
import javax.inject.Singleton

interface LocationsRepository {
    suspend fun getPopularLocations(): List<LocationListItem>
}

@Singleton
class LocationsRepositoryImpl @Inject constructor() : LocationsRepository {
    override suspend fun getPopularLocations(): List<LocationListItem> {
        return listOf(
            LocationListItem.MyLocation,
//            LocationListItem.Location(
//
//            )
        )
    }
}