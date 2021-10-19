package com.hausberger.mysuperapp.business.data.network.abstraction

import com.hausberger.mysuperapp.business.domain.model.Place

interface PlaceNetworkDataSource {

    suspend fun createPlace(place: Place): String
}