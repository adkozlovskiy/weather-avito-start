package com.kozlovskiy.avitoweather.data.api.model

import com.google.gson.annotations.SerializedName
import com.kozlovskiy.avitoweather.domain.model.summary.OneCall
import com.kozlovskiy.avitoweather.domain.util.IconResolver

data class OneCallResponse(
    @SerializedName("current")
    val currentResponse: CurrentResponse,
    @SerializedName("daily")
    val dailyResponses: List<DailyResponse>,
    @SerializedName("hourly")
    val hourlyResponses: List<HourlyResponse>,
) {
    fun toOneCall(iconResolver: IconResolver): OneCall {
        return OneCall(
            current = currentResponse.toCurrent(iconResolver),
            dailies = dailyResponses.map { it.toDaily(iconResolver) },
            hourlies = hourlyResponses
                .take(MAX_HOURLIES_COUNT)
                .map { it.toHourly(iconResolver) }
        )
    }

    companion object {
        const val MAX_HOURLIES_COUNT = 12
    }
}