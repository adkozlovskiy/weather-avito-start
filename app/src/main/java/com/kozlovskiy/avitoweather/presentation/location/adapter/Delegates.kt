package com.kozlovskiy.avitoweather.presentation.location.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.kozlovskiy.avitoweather.databinding.LocationItemBinding
import com.kozlovskiy.avitoweather.databinding.MyLocationItemBinding
import com.kozlovskiy.avitoweather.domain.model.ListLocation
import com.kozlovskiy.avitoweather.domain.model.LocationListItem
import com.kozlovskiy.avitoweather.domain.model.MyLocation

fun listLocationDelegate(
    onLocationSelected: (ListLocation) -> Unit,
) = adapterDelegateViewBinding<ListLocation, LocationListItem, LocationItemBinding>(
    { layoutInflater, parent ->
        LocationItemBinding.inflate(layoutInflater, parent, false)
    }
) {
    bind {
        val stateInfo = item.state + ", " + item.country
        with(binding) {
            locality.text = item.locality
            state.text = stateInfo

            root.setOnClickListener {
                onLocationSelected(item)
            }
        }
    }
}

fun myLocationDelegate(
    onSelected: () -> Unit,
) = adapterDelegateViewBinding<MyLocation, LocationListItem, MyLocationItemBinding>(
    { layoutInflater, parent ->
        MyLocationItemBinding.inflate(layoutInflater, parent, false)
    }
) {
    bind {
        binding.root.setOnClickListener { onSelected() }
    }
}