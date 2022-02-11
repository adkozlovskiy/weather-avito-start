package com.kozlovskiy.avitoweather.presentation.summary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kozlovskiy.avitoweather.databinding.DailyItemBinding
import com.kozlovskiy.avitoweather.domain.model.Daily

class DailyWeatherAdapter : ListAdapter<Daily, DailyWeatherAdapter.DailyWeatherViewHolder>(
    DailyDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val binding = DailyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DailyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val currentDaily = getItem(position)
        holder.bind(currentDaily)
    }

    class DailyWeatherViewHolder(
        private val binding: DailyItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(daily: Daily) {
            binding.apply {
                tvDate.text = daily.date
                tvDay.text = daily.day
                tvTempDay.text = daily.tempDay
                tvTempNight.text = daily.tempNight

                ivIcon.setImageDrawable(daily.icon)
            }
        }
    }

    class DailyDiffCallback : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem.date == oldItem.date
        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem == newItem
        }
    }
}