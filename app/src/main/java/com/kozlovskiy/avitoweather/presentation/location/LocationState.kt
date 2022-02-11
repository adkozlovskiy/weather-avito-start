package com.kozlovskiy.avitoweather.presentation.location

import com.kozlovskiy.avitoweather.domain.model.SimpleLocation

data class LocationState(
    val locations: List<SimpleLocation> = emptyList(),
    val currentLocation: SimpleLocation? = null,
    val error: Exception? = null,
    val loading: Boolean = false,
)