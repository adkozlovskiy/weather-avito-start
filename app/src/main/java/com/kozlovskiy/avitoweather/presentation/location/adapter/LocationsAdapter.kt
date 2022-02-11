package com.kozlovskiy.avitoweather.presentation.location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.kozlovskiy.avitoweather.domain.model.LocationListItem
import com.kozlovskiy.avitoweather.presentation.location.adapter.base.BaseLocationsDelegate
import com.kozlovskiy.avitoweather.presentation.location.adapter.base.BaseLocationsViewHolder

class LocationsAdapter(
    private val delegates: List<BaseLocationsDelegate<*, *>>
) : ListAdapter<LocationListItem, BaseLocationsViewHolder<ViewBinding, LocationListItem>>(
    LocationItemDiffCallback(delegates)
) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseLocationsViewHolder<ViewBinding, LocationListItem> {
        val inflater = LayoutInflater.from(parent.context)
        return delegates.find { it.getLayoutId() == viewType }
            ?.getViewHolder(inflater, parent)
            ?.let { it as BaseLocationsViewHolder<ViewBinding, LocationListItem> }
            ?: throw IllegalArgumentException()
    }

    override fun onBindViewHolder(
        holder: BaseLocationsViewHolder<ViewBinding, LocationListItem>,
        position: Int
    ) {
        val location = getItem(position)
        holder.bind(location)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return delegates.find { it.isRelativeItem(item) }
            ?.getLayoutId()
            ?: throw IllegalArgumentException()
    }
}