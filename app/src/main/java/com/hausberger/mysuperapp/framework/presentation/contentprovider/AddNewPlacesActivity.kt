package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hausberger.mysuperapp.databinding.ActivityAddNewPlacesBinding
import com.hausberger.mysuperapp.framework.datasource.cache.database.Database
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNewPlacesActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddNewPlacesBinding

    @Inject
    lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}