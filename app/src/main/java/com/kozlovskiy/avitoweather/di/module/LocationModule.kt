package com.kozlovskiy.avitoweather.di.module

import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.kozlovskiy.avitoweather.di.qualifier.LocationProvider
import com.kozlovskiy.avitoweather.di.qualifier.ProviderType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module(
    includes = [
        LocationProvidersModule::class
    ]
)
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideFusedLocationProvider(
        @ApplicationContext appContext: Context,
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(appContext)
    }

    @Provides
    @Singleton
    fun provideLocationManager(
        @ApplicationContext appContext: Context,
    ): LocationManager {
        return appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Provides
    @Singleton
    fun provideGeocoder(
        @ApplicationContext appContext: Context,
    ): Geocoder {
        return Geocoder(appContext, Locale.getDefault())
    }

    @Provides
    fun provideLocationRequest(): LocationRequest {
        return LocationRequest
            .create()
            .setPriority(PRIORITY_HIGH_ACCURACY)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object LocationProvidersModule {

    @Provides
    @LocationProvider(type = ProviderType.GPS_PROVIDER)
    fun provideGpsLocationProvider(): String {
        return LocationManager.GPS_PROVIDER
    }

    @Provides
    @LocationProvider(type = ProviderType.NETWORK_PROVIDER)
    fun provideNetworkLocationProvider(): String {
        return LocationManager.NETWORK_PROVIDER
    }
}