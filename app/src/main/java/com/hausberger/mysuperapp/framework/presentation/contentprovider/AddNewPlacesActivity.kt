package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hausberger.mysuperapp.databinding.ActivityAddNewPlacesBinding

class AddNewPlacesActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddNewPlacesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}