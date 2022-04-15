package com.kozlovskiy.avitoweather.presentation.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kozlovskiy.avitoweather.domain.usecase.GetWeatherUseCase
import com.kozlovskiy.avitoweather.domain.usecase.WeatherResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
) : ViewModel() {

    private val _summaryState = MutableStateFlow(value = SummaryState())
    val summaryState = _summaryState.asStateFlow()

    init {
        loadWeather()
    }

    fun loadWeather() = viewModelScope.launch {
        if (_summaryState.value.loading) {
            return@launch
        }

        getWeatherUseCase().collect { result ->
            when (result) {
                is WeatherResult.Loading -> {
                    _summaryState.update { it.copy(loading = true, failure = null) }
                }
                is WeatherResult.Failure -> {
                    _summaryState.update {
                        it.copy(
                            loading = false,
                            failure = SummaryState.FailureInfo.Unknown(result.exception)
                        )
                    }
                }
                is WeatherResult.Success -> {
                    _summaryState.update {
                        it.copy(
                            loading = false,
                            location = result.location.locality,
                            current = result.oneCall.current,
                            daily = result.oneCall.dailies,
                            hourly = result.oneCall.hourlies
                        )
                    }
                }
                is WeatherResult.NullLocation -> {
                    _summaryState.update {
                        it.copy(
                            loading = false,
                            failure = SummaryState.FailureInfo.BadLocation
                        )
                    }
                }
                is WeatherResult.NoPermission -> {
                    _summaryState.update {
                        it.copy(
                            loading = false,
                            failure = SummaryState.FailureInfo.NoLocationPermission
                        )
                    }
                }
            }
        }
    }

    fun suppressError() {
        _summaryState.update { it.copy(failure = null) }
    }
}