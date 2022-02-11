package com.kozlovskiy.avitoweather.presentation.location.adapter.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.kozlovskiy.avitoweather.domain.model.LocationListItem

abstract class BaseLocationsViewHolder<out V : ViewBinding, in I : LocationListItem>(
    binding: V
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: I)
}