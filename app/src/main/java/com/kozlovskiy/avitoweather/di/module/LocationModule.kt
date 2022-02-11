package com.kozlovskiy.avitoweather.di.module

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
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
    fun provideGeocoder(
        @ApplicationContext appContext: Context,
    ): Geocoder {
        return Geocoder(appContext, Locale.getDefault())
    }
}