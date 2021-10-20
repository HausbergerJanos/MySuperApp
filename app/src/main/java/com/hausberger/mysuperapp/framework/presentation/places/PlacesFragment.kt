package com.hausberger.mysuperapp.framework.presentation.places

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hausberger.mysuperapp.R
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.databinding.FragmentPlacesBinding
import com.hausberger.mysuperapp.framework.presentation.contentprovider.PlaceAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PlacesFragment : Fragment(R.layout.fragment_places), PlaceAdapter.Interactor {
    private var currentBinding: FragmentPlacesBinding? = null
    private val binding get() = currentBinding!!

    private val viewModel: PlacesViewModel by viewModels()

    private lateinit var placeAdapter: PlaceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentBinding = FragmentPlacesBinding.bind(view)

        setupRecyclerView()

        binding.addNewPlace.apply {
            setOnClickListener {
                val action = PlacesFragmentDirections.actionPlacesFragmentToPlaceDetailFragment(null)
                findNavController().navigate(action)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.places.collect { places ->
                places?.let { safePlaces ->
                    displayPlaces(safePlaces)
                }
            }
        }
    }

    override fun onPlaceClick(place: Place) {
        val action = PlacesFragmentDirections.actionPlacesFragmentToPlaceDetailFragment(place)
        findNavController().navigate(action)
    }

    private fun setupRecyclerView() {
        placeAdapter = PlaceAdapter(this)

        binding.placeRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = placeAdapter
        }
    }

    private fun displayPlaces(places: List<Place>) {
        placeAdapter.submitList(places)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentBinding = null
    }
}