package com.kozlovskiy.avitoweather.presentation.summary

import com.kozlovskiy.avitoweather.domain.model.summary.Current
import com.kozlovskiy.avitoweather.domain.model.summary.Daily
import com.kozlovskiy.avitoweather.domain.model.summary.Hourly

data class SummaryState(
    val location: String? = null,
    val current: Current? = null,
    val hourly: List<Hourly> = emptyList(),
    val daily: List<Daily> = emptyList(),
    val loading: Boolean = false,
    val failure: FailureInfo? = null,
) {
    sealed class FailureInfo {
        object NoLocationPermission : FailureInfo()
        object BadLocation : FailureInfo()
        class Unknown(val ex: Exception) : FailureInfo()
    }
}