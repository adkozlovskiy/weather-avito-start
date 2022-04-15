package com.kozlovskiy.avitoweather.domain.model

enum class Unit(val serialized: String) {
    CELSIUS(serialized = "metric"),
    FAHRENHEIT(serialized = "imperial");
}