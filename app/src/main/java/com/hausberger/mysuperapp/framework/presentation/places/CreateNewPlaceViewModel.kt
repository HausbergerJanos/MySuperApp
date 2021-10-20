package com.hausberger.mysuperapp.framework.presentation.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.business.interactors.CreatePlaceInteractor
import com.hausberger.mysuperapp.business.interactors.DeletePlaceInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewPlaceViewModel
@Inject
constructor(
    private val createPlaceInteractor: CreatePlaceInteractor,
    private val deletePlaceInteractor: DeletePlaceInteractor
) : ViewModel() {

    private val _saved: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val saved = _saved.asStateFlow()

    fun createPlace(place: Place) {
        viewModelScope.launch(IO) {
            createPlaceInteractor.cratePlace(
                place = place
            )

            _saved.value = true
        }
    }

    fun deletePlace(place: Place) {
        viewModelScope.launch(IO) {
            deletePlaceInteractor.deletePlace(
                place = place
            )

            _saved.value = true
        }
    }
}