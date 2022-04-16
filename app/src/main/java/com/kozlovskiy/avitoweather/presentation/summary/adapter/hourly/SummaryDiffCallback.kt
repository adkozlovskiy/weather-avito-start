package com.kozlovskiy.avitoweather.presentation.summary.adapter.hourly

import androidx.recyclerview.widget.DiffUtil
import com.kozlovskiy.avitoweather.domain.model.summary.SummaryListItem

class SummaryDiffCallback : DiffUtil.ItemCallback<SummaryListItem>() {
    override fun areItemsTheSame(oldItem: SummaryListItem, newItem: SummaryListItem): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }

        return oldItem == oldItem
    }

    override fun areContentsTheSame(oldItem: SummaryListItem, newItem: SummaryListItem): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }

        return oldItem == newItem
    }
}