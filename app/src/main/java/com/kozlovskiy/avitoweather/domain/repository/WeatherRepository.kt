package com.kozlovskiy.avitoweather.domain.repository

import com.kozlovskiy.avitoweather.common.Result
import com.kozlovskiy.avitoweather.domain.model.OneCall
import com.kozlovskiy.avitoweather.domain.model.SimpleLocation

interface WeatherRepository {
    suspend fun getOneCallWeather(location: SimpleLocation): Result<OneCall>
}