package com.kozlovskiy.avitoweather.domain.model.location

data class SimpleLocation(
    val latitude: Float,
    val longitude: Float,
    val locality: String? = null,
    val country: String? = null,
    val state: String? = null,
) {
    fun withLocality(name: String): SimpleLocation {
        return this.copy(locality = name)
    }

    fun toListLocation(): ListLocation {
        return ListLocation(
            locality = locality,
            country = country,
            state = state,
            latitude = latitude,
            longitude = longitude
        )
    }
}