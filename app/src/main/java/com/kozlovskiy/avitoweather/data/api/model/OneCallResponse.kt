package com.kozlovskiy.avitoweather.data.api.model

import com.google.gson.annotations.SerializedName
import com.kozlovskiy.avitoweather.domain.model.summary.OneCall
import com.kozlovskiy.avitoweather.domain.util.AdditionalCurrentMapper
import com.kozlovskiy.avitoweather.domain.util.IconResolver

data class OneCallResponse(
    @SerializedName("current")
    val currentResponse: CurrentResponse,
    @SerializedName("daily")
    val dailyResponses: List<DailyResponse>,
    @SerializedName("hourly")
    val hourlyResponses: List<HourlyResponse>,
) {
    fun toOneCall(iconResolver: IconResolver, additionalMapper: AdditionalCurrentMapper): OneCall {
        return OneCall(
            current = currentResponse.toCurrent(iconResolver),
            dailies = dailyResponses.map { it.toDaily(iconResolver) }
                .subList(1, dailyResponses.size),
            hourlies = listOf(
                additionalMapper.map(currentResponse)
            ) + hourlyResponses
                .subList(1, MAX_HOURLIES_SIZE)
                .map { it.toHourly(iconResolver) }
        )
    }

    companion object {
        const val MAX_HOURLIES_SIZE = 14
    }
}