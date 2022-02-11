package com.kozlovskiy.avitoweather.presentation.location

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kozlovskiy.avitoweather.domain.model.SimpleLocation
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
) : ViewModel() {

    private val _locationState = MutableStateFlow(LocationState())
    val locationState = _locationState.asStateFlow()

    fun onLocationSelected(location: SimpleLocation) = viewModelScope
        .launch {
            _locationState.update { it.copy(currentLocation = location) }
        }

    fun searchForLocations(query: String?) = viewModelScope.launch {
        if (query.isNullOrEmpty()) {
            return@launch
        }

        searchLocationsUseCase(query).collect { result ->
            Log.d("TAG", "searchForLocations: $result")
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
                            locations = result.locations
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
}