package com.kozlovskiy.avitoweather.domain.model.location

sealed class LocationListItem

data class ListLocation(
    val locality: String?,
    val country: String?,
    val state: String?,
    val latitude: Float,
    val longitude: Float,
) : LocationListItem() {
    fun toSimpleLocation(): SimpleLocation {
        return SimpleLocation(latitude, longitude, locality, country, state)
    }
}

object MyLocation : LocationListItem()