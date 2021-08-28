package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hausberger.mysuperapp.databinding.ActivityPlacesBinding

class PlacesActivity : AppCompatActivity() {

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
    }
}