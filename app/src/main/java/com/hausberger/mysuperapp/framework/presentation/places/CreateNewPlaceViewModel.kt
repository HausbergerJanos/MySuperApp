package com.hausberger.mysuperapp.framework.presentation.places

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.business.interactors.CreatePlaceInteractor
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateNewPlaceViewModel
@Inject
constructor(
    private val createPlaceInteractor: CreatePlaceInteractor
) : ViewModel() {

    suspend fun createPlace(
        place: Place
    ): Boolean {
        return createPlaceInteractor.cratePlace(
            place = place
        )
    }
}