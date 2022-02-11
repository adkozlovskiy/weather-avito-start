package com.kozlovskiy.avitoweather.presentation.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kozlovskiy.avitoweather.domain.model.LocationListItem
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
) : ViewModel() {

    private val _locationState = MutableStateFlow(LocationState())
    val locationState = _locationState.asStateFlow()

//    fun onLocationSelected(location: SimpleLocation) = viewModelScope
//        .launch {
//            _locationState.update { it.copy(currentLocation = location) }
//        }

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
                                LocationListItem.Location.fromSimpleLocation(
                                    simpleLocation
                                )
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

    private fun setDefaultLocations() = viewModelScope.launch {
        val popularLocations = getPopularLocationsUseCase()
        _locationState.update {
            it.copy(locations = popularLocations)
        }
    }
}