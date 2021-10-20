package com.hausberger.mysuperapp.business.data.cache.implementation

import com.hausberger.mysuperapp.business.data.cache.abstraction.PlaceCacheDataSource
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.framework.datasource.cache.abstraction.PlaceDaoService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaceCacheDataSourceImpl
@Inject
constructor(
    private val placeDaoService: PlaceDaoService
) : PlaceCacheDataSource {

    override suspend fun insertPlace(place: Place): Long {
        return placeDaoService.insertPlace(place)
    }

    override suspend fun getPlaceById(id: String): Place {
        return placeDaoService.getPlaceById(id)
    }

    override suspend fun getPlaces(): Flow<List<Place>> {
        return placeDaoService.getPlaces()
    }

    override suspend fun updatePlace(id: String, synced: Boolean): Int {
        return placeDaoService.updatePlace(id, synced)
    }

    override suspend fun deletePlace(id: String): Int {
        return placeDaoService.deletePlace(id)
    }
}