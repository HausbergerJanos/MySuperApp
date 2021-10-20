package com.hausberger.mysuperapp.business.data.network.implementation

import com.hausberger.mysuperapp.business.data.network.abstraction.PlaceNetworkDataSource
import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.framework.datasource.network.abstraction.PlaceNetworkService
import javax.inject.Inject

class PlaceNetworkDataSourceImpl
@Inject
constructor(
    private val placeNetworkService: PlaceNetworkService
) : PlaceNetworkDataSource {

    override suspend fun insertOrUpdatePlace(place: Place) {
        return placeNetworkService.insertOrUpdatePlace(place)
    }

    override suspend fun deletePlace(place: Place) {
        placeNetworkService.deletePlace(place)
    }
}