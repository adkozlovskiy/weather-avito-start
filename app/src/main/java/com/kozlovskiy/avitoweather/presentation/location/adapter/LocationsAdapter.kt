package com.kozlovskiy.avitoweather.presentation.location.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.kozlovskiy.avitoweather.domain.model.location.ListLocation
import com.kozlovskiy.avitoweather.domain.model.location.LocationListItem

class LocationsAdapter(
    onLocationSelected: (ListLocation) -> Unit,
    onMyLocationSelected: () -> Unit,
) : AsyncListDifferDelegationAdapter<LocationListItem>(
    LocationsDiffCallback(),
    listLocationDelegate(onLocationSelected),
    myLocationDelegate(onMyLocationSelected)
)