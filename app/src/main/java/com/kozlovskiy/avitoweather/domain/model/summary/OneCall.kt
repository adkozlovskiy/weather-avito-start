package com.kozlovskiy.avitoweather.domain.model.summary

data class OneCall(
    val current: Current,
    val dailies: List<Daily>,
    val hourlies: List<Hourly>,
)