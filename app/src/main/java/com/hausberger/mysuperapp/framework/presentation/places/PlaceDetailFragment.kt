package com.hausberger.mysuperapp.framework.presentation.places

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hausberger.mysuperapp.R
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.databinding.FragmentPlaceDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaceDetailFragment : Fragment(R.layout.fragment_place_detail) {
    private var currentBinding: FragmentPlaceDetailBinding? = null
    private val binding get() = currentBinding!!

    val args: PlaceDetailFragmentArgs by navArgs()

    private val viewModel: CreateNewPlaceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentBinding = FragmentPlaceDetailBinding.bind(view)

        bind(args.place)
    }

    private fun bind(place: Place?) {
        place?.let { safePlace ->
            binding.apply {
                saveButton.text = "Update"
                townEditText.setText(safePlace.town)
                countryEditText.setText(safePlace.country)
                saveButton.setOnClickListener {
                    updatePlace()
                }
            }

            setHasOptionsMenu(true)
        } ?: run {
            binding.saveButton.apply {
                savePlace()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_place -> {
                deletePlace()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updatePlace() {
        Toast.makeText(requireContext(), "Update", Toast.LENGTH_SHORT).show()
    }

    private fun deletePlace() {
        Toast.makeText(requireContext(), "Delete", Toast.LENGTH_SHORT).show()
    }

    private fun savePlace() {
        val town = binding.townEditText.text.toString().trim()
        val country = binding.countryEditText.text.toString().trim()

        if (town.isNotEmpty() && country.isNotEmpty()) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                val place = Place(
                    town = town,
                    country = country,
                    synced = false
                )

                val success = viewModel.createPlace(
                    place
                )

                withContext(Dispatchers.Main) {
                    if (success) {
                        clearScreen()
                        Toast.makeText(
                            requireContext(),
                            "Place has been saved!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Something went wrong...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } }
    }

    private fun clearScreen() {
        binding.townEditText.text.clear()
        binding.countryEditText.text.clear()

        val imm: InputMethodManager =
            requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = requireActivity().currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(requireContext())
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)

        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentBinding = null
    }
}