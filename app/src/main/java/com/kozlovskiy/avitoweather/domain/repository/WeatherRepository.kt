package com.kozlovskiy.avitoweather.domain.repository

import com.kozlovskiy.avitoweather.common.Result
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation
import com.kozlovskiy.avitoweather.domain.model.summary.Current
import com.kozlovskiy.avitoweather.domain.model.summary.OneCall

interface WeatherRepository {
    suspend fun getCurrentWeather(location: SimpleLocation): Result<Current>
    suspend fun getOneCallWeather(location: SimpleLocation): Result<OneCall>
}