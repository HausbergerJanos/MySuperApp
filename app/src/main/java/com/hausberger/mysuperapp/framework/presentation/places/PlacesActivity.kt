package com.hausberger.mysuperapp.framework.presentation.places

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hausberger.mysuperapp.databinding.ActivityPlacesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlacesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}