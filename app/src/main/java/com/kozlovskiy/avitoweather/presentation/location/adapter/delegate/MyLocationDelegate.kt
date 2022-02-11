package com.kozlovskiy.avitoweather.presentation.location.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.databinding.MyLocationItemBinding
import com.kozlovskiy.avitoweather.domain.model.LocationListItem
import com.kozlovskiy.avitoweather.presentation.location.adapter.base.BaseLocationsDelegate
import com.kozlovskiy.avitoweather.presentation.location.adapter.base.BaseLocationsViewHolder

class MyLocationDelegate :
    BaseLocationsDelegate<MyLocationItemBinding, LocationListItem.MyLocation> {
    override fun isRelativeItem(item: LocationListItem): Boolean {
        return item is LocationListItem.MyLocation
    }

    override fun getLayoutId(): Int = R.layout.my_location_item

    override fun getViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): BaseLocationsViewHolder<MyLocationItemBinding, LocationListItem.MyLocation> {
        val binding = MyLocationItemBinding.inflate(inflater, parent, false)
        return MyLocationViewHolder(binding)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<LocationListItem.MyLocation> {
        return MyLocationDiffCallback()
    }

    class MyLocationDiffCallback : DiffUtil.ItemCallback<LocationListItem.MyLocation>() {
        override fun areItemsTheSame(
            oldItem: LocationListItem.MyLocation,
            newItem: LocationListItem.MyLocation
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: LocationListItem.MyLocation,
            newItem: LocationListItem.MyLocation
        ): Boolean {
            return true
        }
    }

    class MyLocationViewHolder(
        binding: MyLocationItemBinding,
    ) : BaseLocationsViewHolder<MyLocationItemBinding, LocationListItem.MyLocation>(binding) {
        override fun bind(item: LocationListItem.MyLocation) {}
    }
}