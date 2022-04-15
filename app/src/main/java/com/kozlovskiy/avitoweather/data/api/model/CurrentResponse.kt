package com.kozlovskiy.avitoweather.data.api.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.kozlovskiy.avitoweather.domain.model.summary.Current
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
    @SerializedName("weather")
    val weather: List<WeatherResponse>,
) {
    fun toCurrent(iconResolver: IconResolver): Current {
        return Current(
            temp = ScaleUtils.toDegreesString(temp),
            feelsLike = ScaleUtils.toDegreesString(feelsLike),
            description = weather[0].description.replaceFirstChar {
                it.uppercase()
            },
            icon = iconResolver.resolve(weather[0].icon)
        ).also {
            Log.d("TAG", "toCurrent: $it")
        }
    }
}