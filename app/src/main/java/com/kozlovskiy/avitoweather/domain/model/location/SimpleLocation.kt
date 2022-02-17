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
}