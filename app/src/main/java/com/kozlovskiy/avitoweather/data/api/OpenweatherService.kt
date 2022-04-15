package com.kozlovskiy.avitoweather.data.api

import com.kozlovskiy.avitoweather.data.api.model.GeocoderResponse
import com.kozlovskiy.avitoweather.data.api.model.OneCallResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenweatherService {

    @GET("data/2.5/onecall")
    suspend fun getOneCallWeather(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float
    ): OneCallResponse

    @GET("geo/1.0/direct")
    suspend fun getLocationsByQuery(
        @Query("q") query: String,
        @Query("limit") limit: Int,
    ): List<GeocoderResponse>
}