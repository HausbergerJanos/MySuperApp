package com.hausberger.mysuperapp.framework.datasource.network.abstraction

import com.hausberger.mysuperapp.business.domain.model.Place

interface PlaceNetworkService {

    suspend fun createPlace(place: Place): String

    suspend fun deletePlace(place: Place)
}