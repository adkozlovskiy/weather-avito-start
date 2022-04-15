package com.kozlovskiy.avitoweather.domain.model.location

/**
 * Не для использования в списке локаций.
 */
data class SimpleLocation(
    val latitude: Float,
    val longitude: Float,
    /**
     * Одно неразделенное значение (либо локалити, либо страна)
     */
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