package com.kozlovskiy.avitoweather.presentation.summary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kozlovskiy.avitoweather.databinding.HourlyItemBinding
import com.kozlovskiy.avitoweather.domain.model.Hourly

class HourlyWeatherAdapter : ListAdapter<Hourly, HourlyWeatherAdapter.HourlyWeatherViewHolder>(
    HourlyDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val binding = HourlyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HourlyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val currentDaily = getItem(position)
        holder.bind(currentDaily)
    }

    class HourlyWeatherViewHolder(
        private val binding: HourlyItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hourly: Hourly) {
            binding.apply {
                tvTemp.text = hourly.temp
                tvTime.text = hourly.time

                ivIcon.setImageDrawable(hourly.icon)
            }
        }
    }

    class HourlyDiffCallback : DiffUtil.ItemCallback<Hourly>() {
        override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
            return oldItem.time == oldItem.time
        }

        override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
            return oldItem == newItem
        }
    }
}