package com.kozlovskiy.avitoweather.domain.repository

import com.kozlovskiy.avitoweather.common.Result
import com.kozlovskiy.avitoweather.domain.model.SimpleLocation

interface GeocoderRepository {
    suspend fun getLocationsByQuery(query: String, limit: Int): Result<List<SimpleLocation>>
}