package com.kozlovskiy.avitoweather.di.module

import com.kozlovskiy.avitoweather.data.repository.GeocoderRepositoryImpl
import com.kozlovskiy.avitoweather.data.repository.WeatherRepositoryImpl
import com.kozlovskiy.avitoweather.domain.repository.GeocoderRepository
import com.kozlovskiy.avitoweather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    fun bindGeocoderRepository(impl: GeocoderRepositoryImpl): GeocoderRepository

}