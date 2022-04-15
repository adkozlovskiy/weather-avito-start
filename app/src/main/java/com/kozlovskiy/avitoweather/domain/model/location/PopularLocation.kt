package com.kozlovskiy.avitoweather.domain.model.location

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
    fun toListLocation(): ListLocation {
        return ListLocation(
            locality = city,
            latitude = lat.toFloat(),
            longitude = lng.toFloat(),
            country = country,
            state = state
        )
    }
}
