package com.kozlovskiy.avitoweather.presentation.summary

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.databinding.SummaryFragmentBinding
import com.kozlovskiy.avitoweather.domain.model.Current
import com.kozlovskiy.avitoweather.presentation.summary.adapter.DailyDividerDecorator
import com.kozlovskiy.avitoweather.presentation.summary.adapter.DailyWeatherAdapter
import com.kozlovskiy.avitoweather.presentation.summary.adapter.HourlyWeatherAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SummaryFragment : Fragment(R.layout.summary_fragment) {

    private val viewModel: SummaryViewModel by viewModels()
    private val binding by viewBinding(SummaryFragmentBinding::bind)

    private val dailyAdapter = DailyWeatherAdapter()
    private val hourlyAdapter = HourlyWeatherAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpClickListeners()
        setUpRecyclerViews()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.summaryState.collect { summaryState ->
                setProgressbarVisible(summaryState.loading)
                Log.d("TAG", "onViewCreated: $summaryState")
                when (summaryState.failure) {
                    is SummaryState.FailureInfo.NoLocationPermission -> {

                    }
                    is SummaryState.FailureInfo.BadLocation -> {

                    }
                    is SummaryState.FailureInfo.Unknown -> {
                        Log.d("TAG", "onViewCreated: ${summaryState.failure.ex}")
                    }
                    null -> {
                        showLocationInfo(summaryState.location)

                        if (summaryState.current != null) {
                            showCurrentWeather(summaryState.current)
                        }
                        dailyAdapter.submitList(summaryState.daily)
                        hourlyAdapter.submitList(summaryState.hourly)
                    }
                }
            }
        }
    }

    private fun showLocationInfo(location: String?) {
        binding.tvLocation.text = location ?: ""
    }

    private fun setUpRecyclerViews() {
        val decorator = DailyDividerDecorator.newInstance(requireContext())
        with(binding) {
            rvHourlies.adapter = hourlyAdapter
            rvDailies.adapter = dailyAdapter
            rvDailies.addItemDecoration(decorator)
        }
    }

    private fun showCurrentWeather(current: Current) {
        with(binding) {
            tvTemp.text = current.temp
            tvTitle.text = current.title
            tvFeelsLike.text = getString(R.string.feels_like, current.feelsLike)

            ivIcon.setImageDrawable(current.icon)
        }
    }

    private fun setProgressbarVisible(visible: Boolean) {
        binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun setUpClickListeners() {
        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_summaryFragment_to_locationFragment)
        }
    }
}