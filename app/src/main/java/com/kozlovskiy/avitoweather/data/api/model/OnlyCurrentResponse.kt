package com.kozlovskiy.avitoweather.data.api.model

import com.google.gson.annotations.SerializedName
import com.kozlovskiy.avitoweather.domain.model.summary.Current
import com.kozlovskiy.avitoweather.domain.util.IconResolver
import com.kozlovskiy.avitoweather.domain.util.ScaleUtils

data class OnlyCurrentResponse(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("weather")
    val weather: List<WeatherResponse>,
    @SerializedName("main")
    val mainResponse: MainResponse,
) {
    fun toCurrent(iconResolver: IconResolver): Current {
        return Current(
            temp = ScaleUtils.toDegreesString(mainResponse.temp),
            feelsLike = ScaleUtils.toDegreesString(mainResponse.feelsLike),
            description = weather[0].description,
            icon = iconResolver.resolve(weather[0].icon)
        )
    }
}

data class MainResponse(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
)