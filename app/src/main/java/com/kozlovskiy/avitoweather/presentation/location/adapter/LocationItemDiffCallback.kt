package com.kozlovskiy.avitoweather.presentation.location.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kozlovskiy.avitoweather.domain.model.LocationListItem
import com.kozlovskiy.avitoweather.presentation.location.adapter.base.BaseLocationsDelegate

class LocationItemDiffCallback(
    private val delegates: List<BaseLocationsDelegate<*, *>>
) : DiffUtil.ItemCallback<LocationListItem>() {

    override fun areItemsTheSame(oldItem: LocationListItem, newItem: LocationListItem): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }

        return getItemCallback(oldItem).areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: LocationListItem, newItem: LocationListItem): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }

        return getItemCallback(oldItem).areContentsTheSame(oldItem, newItem)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getItemCallback(item: LocationListItem): DiffUtil.ItemCallback<LocationListItem> =
        delegates.find { it.isRelativeItem(item) }
            ?.getDiffUtil()
            ?.let { it as DiffUtil.ItemCallback<LocationListItem> }
            ?: throw IllegalArgumentException()

}