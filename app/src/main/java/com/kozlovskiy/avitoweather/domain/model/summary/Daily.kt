package com.kozlovskiy.avitoweather.domain.model.summary

import android.graphics.drawable.Drawable

data class Daily(
    val date: String,
    val day: String,
    val tempDay: String,
    val tempNight: String,
    val icon: Drawable,
)