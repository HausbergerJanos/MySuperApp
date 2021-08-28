package com.hausberger.mysuperapp.framework.presentation.contentprovider

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hausberger.mysuperapp.databinding.ActivityAddNewPlacesBinding
import com.hausberger.mysuperapp.framework.datasource.cache.implementation.PlacesDao
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class AddNewPlacesActivity : AppCompatActivity() {

    @Inject
    lateinit var placesDao: PlacesDao

    lateinit var binding: ActivityAddNewPlacesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.saveButton.apply {
            setOnClickListener(provideSaveClickListener())
        }
    }

    private fun provideSaveClickListener() = View.OnClickListener {
        savePlace()
    }

    private fun savePlace() {
        val town = binding.townEditText.text.toString().trim()
        val country = binding.countryEditText.text.toString().trim()

        if (town.isNotEmpty() && country.isNotEmpty()) {
            GlobalScope.launch(IO) {
                val result = placesDao.insert(
                    PlaceEntity(
                        town = town,
                        country = country
                    )
                )

                withContext(Main) {
                    if (result > 0) {
                        clearScreen()
                        Toast.makeText(this@AddNewPlacesActivity, "Place has been saved!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AddNewPlacesActivity, "Something went wrong...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun clearScreen() {
        binding.townEditText.text.clear()
        binding.countryEditText.text.clear()

        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this@AddNewPlacesActivity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)

        finish()
    }
}