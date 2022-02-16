package com.kozlovskiy.avitoweather.domain.model.summary

import android.graphics.drawable.Drawable

data class Current(
    val temp: String,
    val feelsLike: String,
    val windSpeed: String,
    val windDirection: String,
    val title: String,
    val description: String,
    val icon: Drawable,
)
