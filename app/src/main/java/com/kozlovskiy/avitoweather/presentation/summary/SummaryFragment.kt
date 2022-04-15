package com.kozlovskiy.avitoweather.presentation.summary

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.common.collectOnLifecycle
import com.kozlovskiy.avitoweather.common.getOr
import com.kozlovskiy.avitoweather.databinding.SummaryFragmentBinding
import com.kozlovskiy.avitoweather.domain.model.summary.Current
import com.kozlovskiy.avitoweather.domain.util.DialogUtils
import com.kozlovskiy.avitoweather.presentation.summary.adapter.DailyDividerDecorator
import com.kozlovskiy.avitoweather.presentation.summary.adapter.DailyWeatherAdapter
import com.kozlovskiy.avitoweather.presentation.summary.adapter.HourlyWeatherAdapter
import dagger.hilt.android.AndroidEntryPoint

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

        viewModel.summaryState.collectOnLifecycle(
            lifecycleOwner = viewLifecycleOwner,
            state = Lifecycle.State.STARTED
        ) { summaryState ->
            setProgressbarVisible(summaryState.loading)
            when (summaryState.failure) {
                is SummaryState.FailureInfo.NoLocationPermission -> {
                    requestLocationPermissions()
                }
                is SummaryState.FailureInfo.BadLocation -> {
                    DialogUtils.getBadLocationDialog(
                        requireContext(),
                        onRetryAction = {
                            viewModel.loadWeather()
                        },
                        onDismissAction = {
                            viewModel.suppressError()
                        },
                        onManuallySelectAction = {
                            navigateToLocationFragment()
                        }
                    )
                }
                is SummaryState.FailureInfo.Unknown -> {
                    DialogUtils.getUnknownErrorDialog(
                        requireContext(),
                        onRetryAction = {
                            viewModel.loadWeather()
                        },
                        onDismissAction = {
                            viewModel.suppressError()
                        }
                    )
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

    private fun showLocationInfo(location: String?) {
        binding.toolbar.title = location ?: ""
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
        binding.toolbar.menu
            .findItem(R.id.action_settings)
            .setOnMenuItemClickListener {
                navigateToLocationFragment()
                true
            }
    }

    private fun navigateToLocationFragment() {
        findNavController().navigate(R.id.action_summaryFragment_to_locationFragment)
    }

    private fun requestLocationPermissions() {
        requestPermissionLauncher.launch(
            arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions: Map<String, Boolean> ->
        val finePermissionGranted = permissions.getOr(ACCESS_FINE_LOCATION, false)
        val coarsePermissionGranted = permissions.getOr(ACCESS_COARSE_LOCATION, false)
        if (finePermissionGranted && coarsePermissionGranted) {
            // Granted permissions section.
            viewModel.loadWeather()
        }
    }
}