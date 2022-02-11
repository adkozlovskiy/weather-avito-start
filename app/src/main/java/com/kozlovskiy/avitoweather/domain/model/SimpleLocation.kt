package com.kozlovskiy.avitoweather.domain.model

data class SimpleLocation(
    val latitude: Double,
    val longitude: Double,
    val name: String? = null,
) {
    fun withName(name: String): SimpleLocation {
        return this.copy(name = name)
    }
}