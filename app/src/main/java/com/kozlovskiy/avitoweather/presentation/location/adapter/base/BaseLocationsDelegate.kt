package com.kozlovskiy.avitoweather.presentation.location.adapter.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.kozlovskiy.avitoweather.domain.model.LocationListItem

interface BaseLocationsDelegate<V : ViewBinding, I : LocationListItem> {

    fun isRelativeItem(item: LocationListItem): Boolean

    @LayoutRes
    fun getLayoutId(): Int

    fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseLocationsViewHolder<V, I>

    fun getDiffUtil(): DiffUtil.ItemCallback<I>

}