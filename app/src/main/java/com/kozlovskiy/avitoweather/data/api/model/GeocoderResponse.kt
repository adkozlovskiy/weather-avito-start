package com.kozlovskiy.avitoweather.data.api.model

import com.google.gson.annotations.SerializedName
import com.kozlovskiy.avitoweather.domain.model.SimpleLocation
import java.util.*

data class GeocoderResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("local_names")
    val localNames: Map<String, String>,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("state")
    val state: String?,
    @SerializedName("country")
    val country: String?,
) {
    fun toSimpleLocation(): SimpleLocation {
        return SimpleLocation(
            latitude = latitude,
            longitude = longitude,
            name = localizeName(name, localNames)
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

        val localizedName = StringBuilder()
        localizedName.append("$localized, ")

        if (state != null) {
            localizedName.append("$state, ")
        }

        if (country != null) {
            localizedName.append(country)
        }
        return localizedName.toString()
    }
}