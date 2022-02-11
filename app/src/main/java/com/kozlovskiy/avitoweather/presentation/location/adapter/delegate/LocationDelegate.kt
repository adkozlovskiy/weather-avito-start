package com.kozlovskiy.avitoweather.presentation.location.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.databinding.LocationItemBinding
import com.kozlovskiy.avitoweather.domain.model.LocationListItem
import com.kozlovskiy.avitoweather.presentation.location.adapter.base.BaseLocationsDelegate
import com.kozlovskiy.avitoweather.presentation.location.adapter.base.BaseLocationsViewHolder

class LocationDelegate(
    private val onLocationSelected: (LocationListItem.Location) -> Unit,
) : BaseLocationsDelegate<LocationItemBinding, LocationListItem.Location> {
    override fun isRelativeItem(item: LocationListItem): Boolean {
        return item is LocationListItem.Location
    }

    override fun getLayoutId(): Int = R.layout.location_item

    override fun getViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): BaseLocationsViewHolder<LocationItemBinding, LocationListItem.Location> {
        val binding = LocationItemBinding.inflate(inflater, parent, false)
        return LocationsViewHolder(binding)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<LocationListItem.Location> {
        return LocationDiffCallback()
    }

    class LocationDiffCallback : DiffUtil.ItemCallback<LocationListItem.Location>() {
        override fun areItemsTheSame(
            oldItem: LocationListItem.Location,
            newItem: LocationListItem.Location
        ): Boolean {
            return oldItem.latitude == newItem.latitude
                    && oldItem.longitude == newItem.longitude
        }

        override fun areContentsTheSame(
            oldItem: LocationListItem.Location,
            newItem: LocationListItem.Location
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class LocationsViewHolder(
        private val binding: LocationItemBinding,
    ) : BaseLocationsViewHolder<LocationItemBinding, LocationListItem.Location>(binding) {
        override fun bind(item: LocationListItem.Location) {
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
}