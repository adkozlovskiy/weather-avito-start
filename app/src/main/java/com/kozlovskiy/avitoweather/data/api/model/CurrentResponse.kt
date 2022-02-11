package com.kozlovskiy.avitoweather.data.api.model

import com.google.gson.annotations.SerializedName
import com.kozlovskiy.avitoweather.domain.model.Current
import com.kozlovskiy.avitoweather.domain.util.IconResolver
import com.kozlovskiy.avitoweather.domain.util.ScaleUtils

data class CurrentResponse(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Double,
    @SerializedName("weather")
    val weather: List<WeatherResponse>,
) {
    fun toCurrent(iconResolver: IconResolver): Current {
        return Current(
            temp = ScaleUtils.celsiusFromKelvins(temp),
            feelsLike = ScaleUtils.celsiusFromKelvins(feelsLike),
            windDirection = "",
            windSpeed = "",
            title = weather[0].main,
            description = weather[0].description,
            icon = iconResolver.resolve(weather[0].icon)
        )
    }
}