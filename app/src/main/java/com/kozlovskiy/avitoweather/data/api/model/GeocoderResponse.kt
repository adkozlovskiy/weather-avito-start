package com.kozlovskiy.avitoweather.data.api.model

import com.google.gson.annotations.SerializedName
import com.kozlovskiy.avitoweather.domain.model.location.SimpleLocation
import java.util.*

data class GeocoderResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("local_names")
    val localNames: Map<String, String>,
    @SerializedName("lat")
    val latitude: Float,
    @SerializedName("lon")
    val longitude: Float,
    @SerializedName("state")
    val state: String?,
    @SerializedName("country")
    val country: String?,
) {
    fun toSimpleLocation(): SimpleLocation {
        return SimpleLocation(
            latitude = latitude,
            longitude = longitude,
            locality = localizeName(name, localNames),
            state = state,
            country = country
        )
    }

    private fun localizeName(name: String, localNames: Map<String, String>): String {
        val countryCode = Locale.getDefault()
            .country
            .lowercase()

        val localized = if (localNames.isNullOrEmpty()) {
            name
        } else {
            localNames[countryCode] ?: name
        }

        return localized
    }
}