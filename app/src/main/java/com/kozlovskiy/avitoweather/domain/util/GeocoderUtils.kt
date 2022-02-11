package com.kozlovskiy.avitoweather.domain.util

import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.kozlovskiy.avitoweather.domain.model.SimpleLocation
import javax.inject.Inject

class GeocoderUtils @Inject constructor(
    private val geocoder: Geocoder,
) {

    fun completeLocationWithName(location: SimpleLocation): SimpleLocation {
        val addresses = geocoder.getFromLocation(
            location.latitude, location.longitude, 1
        )
        val address = addresses[0]
        val name = address.locality ?: address.countryName
        return location.withName(name)
    }

    fun getLocationsByQuery(query: String): List<SimpleLocation> {
        val addresses = geocoder.getFromLocationName(query, LOCATIONS_RESULTS_IN_QUERY)
        return addresses.map {
            SimpleLocation(
                latitude = it.latitude,
                longitude = it.longitude,
                name = getNameFromAddress(it)
            )
        }
    }

    private fun getNameFromAddress(address: Address): String {
        return address.getAddressLine(0)
    }

    companion object {
        private const val LOCATIONS_RESULTS_IN_QUERY = 5
    }
}