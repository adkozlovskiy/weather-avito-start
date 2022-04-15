package com.kozlovskiy.avitoweather.domain.repository

import com.kozlovskiy.avitoweather.common.Result
import com.kozlovskiy.avitoweather.domain.model.summary.OneCall
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation

interface WeatherRepository {
    suspend fun getOneCallWeather(location: SimpleLocation): Result<OneCall>
}