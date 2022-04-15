package com.kozlovskiy.avitoweather.presentation.location.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kozlovskiy.avitoweather.domain.model.location.ListLocation
import com.kozlovskiy.avitoweather.domain.model.location.LocationListItem
import com.kozlovskiy.avitoweather.domain.model.location.MyLocation

class LocationsDiffCallback : DiffUtil.ItemCallback<LocationListItem>() {
    override fun areItemsTheSame(oldItem: LocationListItem, newItem: LocationListItem): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }

        return when (oldItem) {
            is ListLocation -> {
                newItem as ListLocation
                (oldItem.latitude == newItem.longitude
                        && oldItem.longitude == newItem.longitude)
            }
            is MyLocation -> true
        }
    }

    override fun areContentsTheSame(oldItem: LocationListItem, newItem: LocationListItem): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }
        return oldItem == newItem
    }
}