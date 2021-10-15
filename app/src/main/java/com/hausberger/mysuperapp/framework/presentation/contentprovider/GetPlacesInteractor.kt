package com.hausberger.mysuperapp.framework.presentation.contentprovider

import com.hausberger.mysuperapp.framework.datasource.cache.implementation.PlacesDao
import javax.inject.Inject

class GetPlacesInteractor
@Inject
constructor(
    private val placesDao: PlacesDao
) {

    fun getPlaces() = placesDao.getPlaces()
}