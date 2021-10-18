package com.hausberger.mysuperapp.business.interactors

import com.hausberger.mysuperapp.business.data.cache.abstraction.PlaceCacheDataSource
import com.hausberger.mysuperapp.framework.datasource.cache.database.PlacesDao
import javax.inject.Inject

class GetPlacesInteractor
@Inject
constructor(
    private val placeCacheDataSource: PlaceCacheDataSource
) {

    suspend fun getPlaces() = placeCacheDataSource.getPlaces()
}