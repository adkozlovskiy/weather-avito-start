package com.kozlovskiy.avitoweather.domain.model.summary

import android.graphics.drawable.Drawable

data class Hourly(
    val time: String,
    val temp: String,
    val icon: Drawable,
) : SummaryListItem()