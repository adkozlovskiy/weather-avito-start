package com.kozlovskiy.avitoweather.data.api.model

import com.google.gson.annotations.SerializedName
import com.kozlovskiy.avitoweather.domain.model.Hourly
import com.kozlovskiy.avitoweather.domain.util.IconResolver
import com.kozlovskiy.avitoweather.domain.util.ScaleUtils

data class HourlyResponse(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Double,
    @SerializedName("weather")
    val weatherResponse: List<WeatherResponse>
) {
    fun toHourly(iconResolver: IconResolver): Hourly {
        return Hourly(
            time = ScaleUtils.timeFromSeconds(dt),
            temp = ScaleUtils.celsiusFromKelvins(temp),
            icon = iconResolver.resolve(weatherResponse[0].icon)
        )
    }
}
