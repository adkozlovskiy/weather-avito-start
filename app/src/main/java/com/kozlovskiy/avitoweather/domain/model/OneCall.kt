package com.kozlovskiy.avitoweather.domain.model

data class OneCall(
    val current: Current,
    val dailies: List<Daily>,
    val hourlies: List<Hourly>,
)