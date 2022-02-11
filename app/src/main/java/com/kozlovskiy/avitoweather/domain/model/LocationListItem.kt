package com.kozlovskiy.avitoweather.domain.model

sealed class LocationListItem {
    data class Location(
        val locality: String?,
        val country: String?,
        val state: String?,
        val latitude: Double,
        val longitude: Double,
    ) : LocationListItem() {
        companion object {
            fun fromSimpleLocation(simpleLocation: SimpleLocation): Location {
                return Location(
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
}
