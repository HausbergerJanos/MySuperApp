package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.databinding.ActivityPlacesBinding
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PlacesActivity : AppCompatActivity() {

    private val viewModel: PlacesViewModel by viewModels()

    private lateinit var binding: ActivityPlacesBinding
    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.addNewPlace.apply {
            setOnClickListener {
                val intent = Intent(this@PlacesActivity, AddNewPlacesActivity::class.java)
                startActivity(intent)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.places.collect { places ->
                places?.let { safePlaces ->
                    displayPlaces(safePlaces)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        placeAdapter = PlaceAdapter()

        binding.placeRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@PlacesActivity)
            adapter = placeAdapter
        }
    }

    private fun displayPlaces(places: List<Place>) {
        placeAdapter.submitList(places)
    }
}