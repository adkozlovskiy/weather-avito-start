package com.kozlovskiy.avitoweather.data.api

import com.kozlovskiy.avitoweather.data.api.model.GeocoderResponse
import com.kozlovskiy.avitoweather.data.api.model.OneCallResponse
import com.kozlovskiy.avitoweather.data.api.model.OnlyCurrentResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenweatherService {

    /**
     * Получить только текущую сводку.
     * @param latitude широта
     * @param longitude долготоа
     */
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
    ): OnlyCurrentResponse

    /**
     * Получить тяжелый объект со сводкой.
     * @param latitude широта
     * @param longitude долготоа
     */
    @GET("data/2.5/onecall")
    suspend fun getOneCallWeather(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("exclude") exclude: String = ApiConstants.EXCLUDE,
    ): OneCallResponse

    /**
     * Получить города из Geocoder API по запросу.
     * @param query запрос
     * @param limit лимит ответов
     */
    @GET("geo/1.0/direct")
    suspend fun getLocationsByQuery(
        @Query("q") query: String,
        @Query("limit") limit: Int,
    ): List<GeocoderResponse>
}