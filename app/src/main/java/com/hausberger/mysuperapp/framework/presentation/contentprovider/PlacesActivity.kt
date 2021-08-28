package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hausberger.mysuperapp.databinding.ActivityPlacesBinding
import com.hausberger.mysuperapp.framework.datasource.cache.database.Database
import com.hausberger.mysuperapp.framework.datasource.cache.implementation.PlacesDao
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PlacesActivity : AppCompatActivity() {

    @Inject
    lateinit var placesDao: PlacesDao

    private lateinit var binding: ActivityPlacesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addNewPlace.apply {
            setOnClickListener {
                val intent = Intent(this@PlacesActivity, AddNewPlacesActivity::class.java)
                startActivity(intent)
            }
        }

        val initialPlaces = arrayOf(
            PlaceEntity(country = "USA", town = "New York"),
            PlaceEntity(country = "USA", town = "Boston"),
            PlaceEntity(country = "Hungary", town = "Budapest"),
            PlaceEntity(country = "Hungary", town = "Debrecen")
        )

        GlobalScope.launch(IO) {
            initialPlaces.forEach {
                placesDao.insert(it)
            }
        }
    }
}