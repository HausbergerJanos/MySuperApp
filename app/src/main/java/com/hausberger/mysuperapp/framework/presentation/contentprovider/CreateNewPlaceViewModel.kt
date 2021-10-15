package com.hausberger.mysuperapp.framework.presentation.contentprovider

import androidx.lifecycle.ViewModel
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateNewPlaceViewModel
@Inject
constructor(
    private val createPlaceInteractor: CreatePlaceInteractor
) : ViewModel() {

    suspend fun createPlace(placeEntity: PlaceEntity): Boolean {
        return createPlaceInteractor.cratePlace(placeEntity)
    }
}