package com.kozlovskiy.avitoweather.presentation.location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kozlovskiy.avitoweather.databinding.SimpleLocationItemBinding
import com.kozlovskiy.avitoweather.domain.model.SimpleLocation

class LocationsAdapter : ListAdapter<SimpleLocation, LocationsAdapter.LocationsViewHolder>(
    SimpleLocationDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val binding = SimpleLocationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LocationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(location)
    }

    class LocationsViewHolder(
        private val binding: SimpleLocationItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(location: SimpleLocation) {
            binding.tvLocationName.text = location.name
        }
    }

    class SimpleLocationDiffCallback : DiffUtil.ItemCallback<SimpleLocation>() {
        override fun areItemsTheSame(oldItem: SimpleLocation, newItem: SimpleLocation): Boolean {
            return oldItem.latitude == newItem.latitude
                    && oldItem.longitude == newItem.longitude
        }

        override fun areContentsTheSame(oldItem: SimpleLocation, newItem: SimpleLocation): Boolean {
            return oldItem == newItem
        }
    }
}