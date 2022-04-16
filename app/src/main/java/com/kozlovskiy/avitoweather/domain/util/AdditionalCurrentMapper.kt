package com.kozlovskiy.avitoweather.domain.util

import android.content.Context
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.data.api.model.CurrentResponse
import com.kozlovskiy.avitoweather.domain.model.summary.AdditionalCurrent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

fun interface AdditionalCurrentMapper {
    fun map(currentResponse: CurrentResponse): AdditionalCurrent
}

@Singleton
class AdditionalCurrentMapperImpl @Inject constructor(
    @ApplicationContext
    private val appContext: Context,
) : AdditionalCurrentMapper {
    override fun map(currentResponse: CurrentResponse): AdditionalCurrent {
        return with(currentResponse) {
            AdditionalCurrent(
                windDeg = windDeg,
                wind = buildWindTemplate(windSpeed, windDeg),
                pressure = appContext.getString(
                    R.string.pressure_template,
                    ScaleUtils.hPaToMmHg(pressure)
                ),
                humidity = "$humidity%"
            )
        }
    }

    private fun buildWindTemplate(speed: Double, deg: Int): String {
        val resolvedDegree = appContext.getString(
            when {
                deg >= 22.5 && deg < 67.5 -> R.string.wind_ne
                deg >= 67.5 && deg < 112.5 -> R.string.wind_e
                deg >= 112.5 && deg < 157.5 -> R.string.wind_se
                deg >= 157.5 && deg < 202.5 -> R.string.wind_s
                deg >= 202.5 && deg < 247.5 -> R.string.wind_sw
                deg >= 247.5 && deg < 292.5 -> R.string.wind_w
                deg >= 292.5 && deg < 337.5 -> R.string.wind_nw
                else -> R.string.wind_n
            }
        )
        return appContext.getString(R.string.wind_template, speed.roundToInt(), resolvedDegree)
    }
}