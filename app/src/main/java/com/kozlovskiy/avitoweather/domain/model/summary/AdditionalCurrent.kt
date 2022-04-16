package com.kozlovskiy.avitoweather.domain.model.summary

data class AdditionalCurrent(
    val windDeg: Int,
    val wind: String,
    val pressure: String,
    val humidity: String,
) : SummaryListItem()
