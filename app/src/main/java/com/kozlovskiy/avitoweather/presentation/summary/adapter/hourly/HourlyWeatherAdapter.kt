package com.kozlovskiy.avitoweather.presentation.summary.adapter.hourly

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.kozlovskiy.avitoweather.domain.model.summary.SummaryListItem

class HourlyWeatherAdapter : AsyncListDifferDelegationAdapter<SummaryListItem>(
    SummaryDiffCallback(),
    hourlyWeatherDelegate(),
    additionalDelegate()
)