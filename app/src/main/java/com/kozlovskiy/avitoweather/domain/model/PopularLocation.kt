package com.kozlovskiy.avitoweather.domain.model

import com.google.gson.annotations.SerializedName

data class PopularLocation(
    @SerializedName("city")
    val city: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("state")
    val state: String,
) {
    fun toLocation(): LocationListItem.Location {
        return LocationListItem.Location(
            locality = city,
            latitude = lat.toDouble(),
            longitude = lng.toDouble(),
            country = country,
            state = state
        )
    }
}
