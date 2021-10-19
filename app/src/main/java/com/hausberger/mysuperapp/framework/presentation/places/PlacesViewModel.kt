package com.hausberger.mysuperapp.framework.presentation.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.business.interactors.GetPlacesInteractor
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel
@Inject
constructor(
    private val getPlacesInteractor: GetPlacesInteractor
) : ViewModel() {

    private val _places: MutableStateFlow<List<Place>?> = MutableStateFlow(null)
    val places = _places.asStateFlow()

    init {
        viewModelScope.launch {
            getPlacesInteractor.getPlaces().collect { places ->
                _places.value = places
            }
        }
    }
}
