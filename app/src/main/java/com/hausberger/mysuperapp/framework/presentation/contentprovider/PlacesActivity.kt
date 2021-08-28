package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hausberger.mysuperapp.databinding.ActivityPlacesBinding
import com.hausberger.mysuperapp.framework.datasource.cache.database.Database
import com.hausberger.mysuperapp.framework.datasource.cache.implementation.PlacesDao
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class PlacesActivity : AppCompatActivity() {

    @Inject
    lateinit var placesDao: PlacesDao

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
    }

    override fun onStart() {
        super.onStart()
        populatePlaces()
    }

    private fun populatePlaces() {
        GlobalScope.launch(IO) {
            var places = placesDao.getPlaces()

            if (places.isNullOrEmpty()) {
                val initialPlaces = arrayOf(
                    PlaceEntity(country = "USA", town = "New York"),
                    PlaceEntity(country = "USA", town = "Boston"),
                    PlaceEntity(country = "Hungary", town = "Budapest"),
                    PlaceEntity(country = "Hungary", town = "Debrecen")
                )

                initialPlaces.forEach {
                    placesDao.insert(it)
                }

                places = initialPlaces.toList()
            }

            withContext(Main) {

                displayPlaces(places)
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

    private fun displayPlaces(places: List<PlaceEntity>) {
        placeAdapter.submitList(places)
    }
}