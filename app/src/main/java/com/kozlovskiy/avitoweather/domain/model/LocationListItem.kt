package com.kozlovskiy.avitoweather.domain.model

sealed class LocationListItem

data class ListLocation(
    val locality: String?,
    val country: String?,
    val state: String?,
    val latitude: Float,
    val longitude: Float,
) : LocationListItem() {
    companion object {
        fun fromSimpleLocation(simpleLocation: SimpleLocation): ListLocation {
            return ListLocation(
                locality = simpleLocation.locality,
                country = simpleLocation.country,
                state = simpleLocation.state,
                latitude = simpleLocation.latitude,
                longitude = simpleLocation.longitude
            )
        }
    }
}

object MyLocation : LocationListItem()