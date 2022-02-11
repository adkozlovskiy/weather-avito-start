package com.kozlovskiy.avitoweather.presentation.location

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.databinding.LocationFragmentBinding
import com.kozlovskiy.avitoweather.presentation.location.adapter.LocationsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LocationFragment : Fragment(R.layout.location_fragment) {

    private val viewModel: LocationViewModel by viewModels()
    private val binding by viewBinding(LocationFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()

        val locationsAdapter = LocationsAdapter()
        binding.rvLocations.adapter = locationsAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.locationState.collect {
                if (it.locations.isEmpty()) {
                    // show no such results
                }
                locationsAdapter.submitList(it.locations)
            }
        }
    }

    private fun setUpToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_search -> {
                    val searchView = it.actionView as SearchView
                    searchView.queryHint = "Enter location"
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            if (!searchView.isIconified) {
                                searchView.isIconified = true
                            }
                            it.collapseActionView()
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            viewModel.searchForLocations(newText)
                            return false
                        }
                    })
                    true
                }
                else -> false
            }
        }
    }
}