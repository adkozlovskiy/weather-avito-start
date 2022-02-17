package com.kozlovskiy.avitoweather.domain.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.domain.model.location.LocationListItem
import com.kozlovskiy.avitoweather.domain.model.location.MyLocation
import com.kozlovskiy.avitoweather.domain.model.location.PopularLocation
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
        val popularCitiesJson = appContext.resources
            .openRawResource(R.raw.popular_cities)
            .bufferedReader()
            .use { it.readText() }

        val popularCities = gson.fromJson<List<PopularLocation>>(
            popularCitiesJson, popularLocationType
        )

        val popularLocations = popularCities.map {
            it.toListLocation()
        }

        return listOf(MyLocation) + popularLocations
    }

    companion object {
        private val popularLocationType = TypeToken.getParameterized(
            List::class.java,
            PopularLocation::class.java
        ).type
    }
}