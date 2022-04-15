package com.kozlovskiy.avitoweather.data.api.model

import com.google.gson.annotations.SerializedName

data class TempResponse(
    @SerializedName("day")
    val day: Double,
    @SerializedName("night")
    val night: Double
)
