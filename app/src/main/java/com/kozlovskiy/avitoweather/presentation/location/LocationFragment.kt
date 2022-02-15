package com.kozlovskiy.avitoweather.presentation.location

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.common.collectOnLifecycle
import com.kozlovskiy.avitoweather.databinding.LocationFragmentBinding
import com.kozlovskiy.avitoweather.presentation.location.adapter.LocationsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : Fragment(R.layout.location_fragment) {

    private val viewModel: LocationViewModel by viewModels()
    private val binding by viewBinding(LocationFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpSearch()

        val locationsAdapter = LocationsAdapter(
            onLocationSelected = { viewModel.onLocationSelected(it) },
            onMyLocationSelected = { viewModel.onMyLocationSelected() }
        )
        binding.rvLocations.adapter = locationsAdapter

        viewModel.locationState.collectOnLifecycle(
            lifecycleOwner = viewLifecycleOwner,
            state = Lifecycle.State.STARTED
        ) {
            setProgressbarVisible(it.loading)

            val emptyQuery = binding.etSearch.text.isNullOrEmpty()
            val emptyLocations = it.locations.isEmpty()

            val noSuchPlace = !emptyQuery && emptyLocations && !it.loading

            setNoSuchPlaceVisible(noSuchPlace)
            setRecyclerViewVisible(!noSuchPlace)

            locationsAdapter.items = it.locations
        }
    }

    private fun setProgressbarVisible(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    private fun setNoSuchPlaceVisible(visible: Boolean) {
        binding.tvNoSuchPlace.isVisible = visible
    }

    private fun setRecyclerViewVisible(visible: Boolean) {
        binding.rvLocations.isVisible = visible
    }

    private fun setUpToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpSearch() {
        binding.etSearch.doAfterTextChanged {
            viewModel.searchForLocations(it.toString())
        }
    }
}