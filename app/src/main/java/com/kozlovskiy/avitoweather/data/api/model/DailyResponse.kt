package com.kozlovskiy.avitoweather.data.api.model

import com.google.gson.annotations.SerializedName
import com.kozlovskiy.avitoweather.domain.model.summary.Daily
import com.kozlovskiy.avitoweather.domain.util.IconResolver
import com.kozlovskiy.avitoweather.domain.util.ScaleUtils

data class DailyResponse(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("temp")
    val temp: TempResponse,
    @SerializedName("feels_like")
    val feelsLike: TempResponse,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Double,
    @SerializedName("weather")
    val weatherResponse: List<WeatherResponse>,
) {
    fun toDaily(iconResolver: IconResolver): Daily {
        return Daily(
            date = ScaleUtils.dateFromSeconds(dt),
            day = ScaleUtils.dayOfWeekFromSeconds(dt),
            tempDay = ScaleUtils.celsiusFromKelvins(temp.day),
            tempNight = ScaleUtils.celsiusFromKelvins(temp.night),
            icon = iconResolver.resolve(weatherResponse[0].icon)
        )
    }
}