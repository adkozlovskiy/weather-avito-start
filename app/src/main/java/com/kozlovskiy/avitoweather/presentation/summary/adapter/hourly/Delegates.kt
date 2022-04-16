package com.kozlovskiy.avitoweather.presentation.summary.adapter.hourly

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.kozlovskiy.avitoweather.databinding.AdditionalCurrentItemBinding
import com.kozlovskiy.avitoweather.databinding.HourlyItemBinding
import com.kozlovskiy.avitoweather.domain.model.summary.AdditionalCurrent
import com.kozlovskiy.avitoweather.domain.model.summary.Hourly
import com.kozlovskiy.avitoweather.domain.model.summary.SummaryListItem

fun hourlyWeatherDelegate() =
    adapterDelegateViewBinding<Hourly, SummaryListItem, HourlyItemBinding>(
        { layoutInflater, parent ->
            HourlyItemBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        bind {
            binding.apply {
                tvTemp.text = item.temp
                tvTime.text = item.time

                ivIcon.setImageDrawable(item.icon)
            }
        }
    }

fun additionalDelegate() =
    adapterDelegateViewBinding<AdditionalCurrent, SummaryListItem, AdditionalCurrentItemBinding>(
        { layoutInflater, parent ->
            AdditionalCurrentItemBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        bind {
            binding.apply {
                tvHumidity.text = item.humidity
                tvPressure.text = item.pressure
                tvWind.text = item.wind

                icWindRotation.rotation = item.windDeg.toFloat()
            }
        }
    }

