package com.kozlovskiy.avitoweather.domain.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kozlovskiy.avitoweather.domain.model.LocationListItem
import com.kozlovskiy.avitoweather.domain.model.PopularLocation
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface LocationsRepository {
    fun getPopularLocations(): List<LocationListItem>
}

@Singleton
class LocationsRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val appContext: Context,
    private val gson: Gson,
) : LocationsRepository {

    override fun getPopularLocations(): List<LocationListItem> {
        val popularCitiesJson = appContext.assets
            .open(POPULAR_CITIES_FILE_NAME)
            .bufferedReader().use {
                it.readText()
            }

        val popularCities = gson.fromJson<List<PopularLocation>>(
            popularCitiesJson, popularLocationType
        )

        val popularLocations = popularCities.map {
            it.toLocation()
        }

        return listOf(LocationListItem.MyLocation) + popularLocations
    }

    companion object {
        const val POPULAR_CITIES_FILE_NAME = "popular_cities.json"
        private val popularLocationType = TypeToken.getParameterized(
            List::class.java,
            PopularLocation::class.java
        ).type
    }
}