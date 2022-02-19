package com.kozlovskiy.avitoweather.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationProvider(
    val type: ProviderType
)

enum class ProviderType {
    GPS_PROVIDER,
    NETWORK_PROVIDER;
}