package com.kozlovskiy.avitoweather.domain.util

import android.location.Geocoder
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation
import javax.inject.Inject

class GeocoderUtils @Inject constructor(
    private val geocoder: Geocoder,
) {

    fun completeLocationWithName(location: SimpleLocation): SimpleLocation {
        val addresses = geocoder.getFromLocation(
            location.latitude.toDouble(),
            location.longitude.toDouble(),
            1
        )
        val address = addresses[0]
        val name = address.locality ?: address.countryName
        return location.withLocality(name)
    }
}