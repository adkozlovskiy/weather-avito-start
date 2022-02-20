package com.kozlovskiy.avitoweather.presentation.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kozlovskiy.avitoweather.domain.SharedPreferenceManager
import com.kozlovskiy.avitoweather.domain.model.location.ListLocation
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation
import com.kozlovskiy.avitoweather.domain.usecase.GetPopularLocationsUseCase
import com.kozlovskiy.avitoweather.domain.usecase.LocationsResult
import com.kozlovskiy.avitoweather.domain.usecase.SearchLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val searchLocationsUseCase: SearchLocationsUseCase,
    private val getPopularLocationsUseCase: GetPopularLocationsUseCase,
    private val sharedPreferenceManager: SharedPreferenceManager,
) : ViewModel() {

    private val _locationState = MutableStateFlow(LocationState())
    val locationState = _locationState.asStateFlow()

    // Save previous location and new selected location.
    private val previousLocation = sharedPreferenceManager.getStoredLocation()
    private var updatedLocation: SimpleLocation? = previousLocation

    fun onLocationSelected(location: ListLocation) = viewModelScope
        .launch {
            updatedLocation = location.toSimpleLocation()
            sharedPreferenceManager.storeLocation(
                updatedLocation!!.latitude,
                updatedLocation!!.longitude
            )
        }

    fun onMyLocationSelected() = viewModelScope.launch {
        updatedLocation = null
        sharedPreferenceManager.chooseMyLocation()
    }

    init {
        setDefaultLocations()
    }

    fun searchForLocations(query: String?) = viewModelScope.launch {
        if (query.isNullOrBlank()) {
            setDefaultLocations()
            return@launch
        }

        searchLocationsUseCase(query).collect { result ->
            when (result) {
                is LocationsResult.Loading -> {
                    _locationState.update {
                        it.copy(loading = true)
                    }
                }
                is LocationsResult.Success -> {
                    _locationState.update {
                        it.copy(
                            loading = false,
                            locations = result.locations.map { simpleLocation ->
                                simpleLocation.toListLocation()
                            }
                        )
                    }
                }
                is LocationsResult.Failure -> {
                    _locationState.update {
                        it.copy(error = it.error, loading = false)
                    }
                }
            }
        }
    }

    fun isLocationWasChanged() = previousLocation != updatedLocation

    private fun setDefaultLocations() = viewModelScope.launch {
        val popularLocations = getPopularLocationsUseCase()
        _locationState.update {
            it.copy(locations = popularLocations)
        }
    }
}