package com.kozlovskiy.avitoweather.presentation.location

import com.kozlovskiy.avitoweather.domain.model.LocationListItem

data class LocationState(
    val locations: List<LocationListItem> = emptyList(),
    val error: Exception? = null,
    val loading: Boolean = false,
)